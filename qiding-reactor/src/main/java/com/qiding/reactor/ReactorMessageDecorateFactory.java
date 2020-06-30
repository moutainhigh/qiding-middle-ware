package com.qiding.reactor;

@FunctionalInterface
public interface ReactorMessageDecorateFactory {
	UserMessage decorate(ReactorMessage reactorMessage);
}
