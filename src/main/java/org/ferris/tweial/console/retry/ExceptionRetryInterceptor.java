package org.ferris.tweial.console.retry;

import java.io.Serializable;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
@Interceptor
@ExceptionRetry
public class ExceptionRetryInterceptor implements Serializable {

    private static final long serialVersionUID = 76675643567899788L;
    private static final int max = 4;

    @Inject
    protected Logger log;

    @AroundInvoke
     public Object retryIfExceptionCaught(InvocationContext ctx) throws Exception {
         Exception caught = null;
         for (int i=1, imax=max; i<=imax; i++) {
             try {
                 return ctx.proceed();
             } catch (Exception e) {
                 caught = e;
                 String msg = String.format("Exception caught on attempt %d of %d", i, imax);
                 if (i == max) {
                    log.warn(msg, e);
                 } else {
                    log.warn(msg);
                 }
             }
             try {
                 Thread.sleep(1000 * 5);
             } catch (InterruptedException e) {}
         }
         throw caught;
     }
}
