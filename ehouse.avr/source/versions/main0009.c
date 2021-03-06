#include "../devices.h"
#include "../debug.h"
#include "../utils.h"
#include "../mess.h"
#include "../decoder.h"
#include "../channel1.h"
#include "../timer.h"
#include "../eeprom.h"
#include "../pin.h"
#include "../output.h"
#include "../main.h"
#include <inttypes.h>
#include <util/delay.h>

/********************************************************************************************
Sterownik bramy gara�owej
Obs�ugiwane komendy:
10 - get status
11 - set status
	01 - CLOSE
	02 - OPEN
	03 - WICKET
	04 - PP

********************************************************************************************/

// ATMEGA328
#if SOFTWARE == 0x0009 && defined (DEVICE_MAIN)

#define MAIN_DEV_MAIN		1	// Sterownik
#define MAIN_DEV_STOP		2	// Przycisk STOP		
#define MAIN_DEV_PP			3	// Przycisk PP
#define MAIN_DEV_OPEN		4	// Przycisk OPEN
#define MAIN_DEV_CLOSE		5	// Przycisk CLOSE
#define MAIN_DEV_ISCLOSE	6	// Czujnik zamkni�cia

volatile uint8_t mainMode;
volatile uint8_t mainChange;


void mainInit() {
	outputSet(MAIN_DEV_STOP, OUTPUT_OFF,0);
	outputSet(MAIN_DEV_PP, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_OPEN, OUTPUT_OFF, 0);
	outputSet(MAIN_DEV_CLOSE, OUTPUT_OFF, 0);
	mainMode = 0;
	mainChange = 0; 
}


void mainThread() {
	
	// Czy zmieni� si� stan
	if(!mainChange) return;
	
	// Zmieni� si� stan	
	mainChange = 0;
	
	// Info o stanie
	messSystemSend(DECODER_SYSTEM_INFO, MAIN_DEV_MAIN, mainMode);
	
	// Wykonanie instrukcji
	switch(mainMode) {
		
		// Zamknij bram�
		case 1: {
			outputSet(MAIN_DEV_CLOSE, OUTPUT_OPEN,0);
			_delay_ms(500);
			outputSet(MAIN_DEV_CLOSE, OUTPUT_OFF, 0);
			break;
		}
		
		// Otworz brame
		case 2: {
			outputSet(MAIN_DEV_OPEN, OUTPUT_OPEN,0);
			_delay_ms(500);
			outputSet(MAIN_DEV_OPEN, OUTPUT_OFF, 0);
			break;
		}
		
		// Otw�rz furtke
		case 3: {
			// Otw�rz bram�
			outputSet(MAIN_DEV_OPEN, OUTPUT_OPEN,0);
			_delay_ms(500);
			outputSet(MAIN_DEV_OPEN, OUTPUT_OFF, 0);
			// Czekaj 5 s
			timerSetWait(5);
			while(timerCheckWait()) mainContinue();
			// Zatrzymaj brame
			outputSet(MAIN_DEV_STOP, OUTPUT_OPEN,0);
			_delay_ms(500);
			outputSet(MAIN_DEV_STOP, OUTPUT_OFF, 0);
			break;
		}
		
		// PP
		case 4: {
			outputSet(MAIN_DEV_PP, OUTPUT_OPEN,0);
			_delay_ms(500);
			outputSet(MAIN_DEV_PP, OUTPUT_OFF, 0);
			break;
		}
		
		default: {
			outputSet(MAIN_DEV_STOP, OUTPUT_OFF,0);
			outputSet(MAIN_DEV_PP, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_OPEN, OUTPUT_OFF, 0);
			outputSet(MAIN_DEV_STOP, OUTPUT_OFF, 0);
			mainMode = 0;
			return;
		}
		
	}
}


void mainRequestReceive(uint8_t ndd, uint8_t com, uint8_t param1, uint8_t param2) {
	if((devicesTab[ndd].type == DEV_TYPE_GATE2) && (com == DECODER_REQUEST_COMMAND_11)) {
		mainMode =  param1;
		mainChange = 1;
		timerSetWait(0);
	}
}


void mainResponseSend(uint16_t add, uint8_t nsd, uint8_t id, uint8_t com, uint8_t param1,uint8_t param2) {
	if(devicesTab[nsd].type == DEV_TYPE_GATE2) {
		messAddressSend(MESS_MODE_RESPONSE,add,nsd,id,DECODER_RESPONSE_OK,0,0);
		MESS_PUT_BYTE(PIN_IS_SET1(MAIN_DEV_ISCLOSE));
		messSend();
	}
}


void mainResponseReceive(uint16_t asd, uint8_t ndd,  uint8_t id, uint8_t com, uint8_t param1, uint8_t param2) {
}


#endif