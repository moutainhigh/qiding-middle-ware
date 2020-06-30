package com.qiding.reactor;


import javax.jws.soap.SOAPBinding;

public class ReactorMessageDecorate  implements ReactorMessageDecorateFactory {

    private ReactorMessage reactorMessage;


	@Override
	public UserMessage decorate(ReactorMessage reactorMessage) {
		return reactorMessage;
	}
}
