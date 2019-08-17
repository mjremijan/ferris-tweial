package org.ferris.tweial.console.log4j;

import org.ferris.tweial.console.log4j.Log4jLoggerProducer;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Log4jLoggerProducerTest {

	@Mock
	InjectionPoint ip;
	
	@SuppressWarnings("rawtypes")
	@Mock
	Bean bean;
	
	Log4jLoggerProducer producer;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		Mockito.when(ip.getBean()).thenReturn(bean);
		producer = new Log4jLoggerProducer();
	}
	
	@Test
	public void tet_get_logger() {
		// setup
		Mockito.when(bean.getBeanClass()).thenReturn(getClass());
		
		// action
		Logger log
			= producer.getLogger(ip);
		
		// assert
		Assert.assertEquals(getClass().getName(), log.getName());
	}
}
