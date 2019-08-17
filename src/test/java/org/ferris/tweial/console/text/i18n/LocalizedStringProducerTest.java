package org.ferris.tweial.console.text.i18n;

import org.ferris.tweial.console.text.i18n.LocalizedStringKey;
import org.ferris.tweial.console.text.i18n.LocalizedString;
import org.ferris.tweial.console.text.i18n.LocalizedStringList;
import org.ferris.tweial.console.text.i18n.LocalizedStringProducer;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import org.ferris.tweial._junit.hamcrest.StartsWithMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LocalizedStringProducerTest {

	@Mock
	InjectionPoint ip;
	
	@Mock
	Annotated annotated;
	
	@Mock
	LocalizedStringKey key;
	
	@Rule
    public ExpectedException expectedEx = ExpectedException.none();
	
	LocalizedStringProducer producer;
	
	@Before
	public void before() {
		Mockito.when(annotated.getAnnotation(LocalizedStringKey.class)).thenReturn(key);
		Mockito.when(ip.getAnnotated()).thenReturn(annotated);
		producer = new LocalizedStringProducer();
	}
	
	@Test
	public void test_get_list_when_key_exists() {
		// setup
		Mockito.when(key.value()).thenReturn("countdown");
		
		// action
		LocalizedStringList countdown
			= producer.getLocalizedStringList(ip);
		
		// assert
		Assert.assertEquals(3, countdown.size());
		Assert.assertEquals("one", countdown.get(0).toString());
		Assert.assertEquals("two", countdown.get(1).toString());
		Assert.assertEquals("three", countdown.get(2).toString());
		
	}
	
	@Test
	public void test_get_empty_list_when_no_key() {
		// setup
		Mockito.when(key.value()).thenReturn("key_dont_exist");
		
		// action
		LocalizedStringList empty
			= producer.getLocalizedStringList(ip);
		
		// assert
		Assert.assertEquals(0, empty.size());
	}
	
	@Test
	public void test_get_string_when_key_exists() {
		// setup
		Mockito.when(key.value()).thenReturn("rita");
		
		// action
		LocalizedString missing
			= producer.getLocalizedString(ip);
		
		// assert
		Assert.assertEquals("red", missing.toString());
	}
	
	@Test
	public void test_get_missing_when_no_key() {
		// setup
		Mockito.when(key.value()).thenReturn("key_dont_exist");
		
		// action
		LocalizedString missing
			= producer.getLocalizedString(ip);
		
		// assert
		Assert.assertEquals("<missing>", missing.toString());
	}
	
	@Test
	public void test_getLocalizedStringKey_throws_exception_if_null() {
		// setup
		Mockito.when(annotated.getAnnotation(LocalizedStringKey.class)).thenReturn(null);
		expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(
            new StartsWithMatcher("InjectionPoint has no @LocalizedStringKey"));
        
        // action
		producer.getResourceBundleKey(annotated);
	}
	
	@Test
	public void test_getAnnotated_ok() {
		// setup
		Mockito.when(ip.getAnnotated()).thenReturn(annotated);
		
		// assert
        Assert.assertEquals(annotated, producer.getAnnotated(ip));
	}
	
	@Test
	public void test_getAnnotated_throws_runtime_exception_if_annotation_is_null() {
		// setup
		Mockito.when(ip.getAnnotated()).thenReturn(null);
		expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage(
            new StartsWithMatcher("InjectionPoint annotated is null"));
        
        // action
		producer.getAnnotated(ip);
	}
}
