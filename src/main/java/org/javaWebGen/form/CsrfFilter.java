package org.javaWebGen.form;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.RandomStringGenerator;
import org.javaWebGen.config.WebConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initialize the session if it does not exist.  Used by WebController @see org.javaWebGen.WebController and 
 * CSRF forms @see CsrfForm to validate a request.
 */
public class CsrfFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(CsrfFilter.class);


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest   httpRequest  = (HttpServletRequest)  request;
		setupSession(httpRequest);
		
		chain.doFilter(request, response);
		
	}

	/**
	 * setup session values:
	 * <ul>
	 * <li>Random seed</li>
	 * <li>client ip</li>
	 * <li>userAgent</li>
	 * </ul>
	 * @param req
	 */
	protected void  setupSession(HttpServletRequest req) {
		//log.debug("========setupSession=========");
		if (req == null) {
			return ;
		}
		
		HttpSession session = req.getSession(false);
		if (session == null) {
			log.info("========begin new session=========");
			session = req.getSession(true);
			RandomStringGenerator generator = new RandomStringGenerator.Builder()
					// .withinRange('a', 'z')
					.build();
			String randomLetters = generator.generate(20);
			String userAgent = req.getHeader("User-Agent");
			String ip = req.getLocalAddr();
			session.setAttribute(WebConst.CSRF_SEED, randomLetters);
			// log.debug("sead="+randomLetters);
			session.setAttribute(WebConst.CSRF_IP, ip);

			session.setAttribute(WebConst.CSRF_AGENT, userAgent);
			log.debug("ip=" + ip + "agent=" + userAgent);
			log.info("========new session:" + req.getRequestedSessionId());
			// log.error("null session How did that happen?");
			// maybe should redirect to homepage
		}else {
			log.debug("====existing session:" + req.getRequestedSessionId());
		}
 
	}
	@Override
	public void destroy() {
		 
		
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 
		
	}
}
