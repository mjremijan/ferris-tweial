package org.ferris.tweial.console.util.version;


import org.ferris.tweial.console.util.version.Version;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VersionTest 
{
	@Spy
	private Version version;
	
	@Mock
	private Package pack;
	
	@Test
	public void test_getPackage_throws_exception() {
		Mockito.when(version.getClass()).thenThrow(new RuntimeException("spy exception"));
		Assert.assertEquals(Version.UNKOWN, version.getImplementationVersion());
	}
	
	@Test
	public void getImplementationTitle_PackageIsNull() 
	{
		// Impossible!
		// You have to use doReturn() for stubbing
		Mockito.doReturn(null).when(version).getPackage();
		Assert.assertEquals(Version.UNKOWN, version.getImplementationTitle());
	}
	
	@Test
	public void getImplementationTitle_PackageIsNotNull() 
	{
		Mockito.when(pack.getImplementationTitle()).thenReturn("my title");
		Mockito.doReturn(pack).when(version).getPackage();
		Assert.assertEquals("my title", version.getImplementationTitle());
	}
	
	@Test
	public void getImplementationVendor_PackageIsNull() 
	{
		// Impossible!
		// You have to use doReturn() for stubbing
		Mockito.doReturn(null).when(version).getPackage();
		Assert.assertEquals(Version.UNKOWN, version.getImplementationVendor());
	}
	
	@Test
	public void getImplementationVendor_PackageIsNotNull() 
	{
		Mockito.when(pack.getImplementationVendor()).thenReturn("my vendor");
		Mockito.doReturn(pack).when(version).getPackage();
		Assert.assertEquals("my vendor", version.getImplementationVendor());
	}
	
	@Test
	public void getImplementationVersion_PackageIsNull() 
	{
		// Impossible!
		// You have to use doReturn() for stubbing
		Mockito.doReturn(null).when(version).getPackage();
		Assert.assertEquals(Version.UNKOWN, version.getImplementationVersion());
	}
	
	@Test
	public void getImplementationVersion_PackageIsNotNull() 
	{
		Mockito.when(pack.getImplementationVersion()).thenReturn("my version");
		Mockito.doReturn(pack).when(version).getPackage();
		Assert.assertEquals("my version", version.getImplementationVersion());
	}
	
	@Test
	public void getImplementationVersion_DefaultPackage() 
	{
		Assert.assertEquals(Version.UNKOWN, version.getImplementationVersion());
	}
}
