package net.vionta.salvora.test.routes.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Locale;
import io.vertx.ext.web.ParsedHeaderValues;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;

public class RoutingContextMock implements RoutingContext {

	HttpServerRequestMock httpServerRequestMock;
	
	@Override
	public HttpServerRequest request() {
		return this.getHttpServerRequestMock() ;
	}

	@Override
	public HttpServerResponse response() {
		return null;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(int statusCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(int statusCode, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RoutingContext put(String key, Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T remove(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> data() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertx vertx() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable String mountPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route currentRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String normalisedPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Cookie getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoutingContext addCookie(io.vertx.core.http.Cookie cookie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoutingContext addCookie(Cookie cookie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Cookie removeCookie(String name, boolean invalidate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int cookieCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Cookie> cookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, io.vertx.core.http.Cookie> cookieMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable String getBodyAsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable String getBodyAsString(String encoding) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable JsonObject getBodyAsJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable JsonArray getBodyAsJsonArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Buffer getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<FileUpload> fileUploads() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Session session() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSessionAccessed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public @Nullable User user() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Throwable failure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public @Nullable String getAcceptableContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParsedHeaderValues parsedHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addHeadersEndHandler(Handler<Void> handler) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean removeHeadersEndHandler(int handlerID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addBodyEndHandler(Handler<Void> handler) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean removeBodyEndHandler(int handlerID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addEndHandler(Handler<AsyncResult<Void>> handler) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean removeEndHandler(int handlerID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean failed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBody(Buffer body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSession(Session session) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAcceptableContentType(@Nullable String contentType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reroute(HttpMethod method, String path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Locale> acceptableLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> pathParams() {
		return null;
	}

	@Override
	public @Nullable String pathParam(String name) {
		return getHttpServerRequestMock().getMockParameters().get(name);
	}

	@Override
	public MultiMap queryParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> queryParam(String name) {
		 
		if( getHttpServerRequestMock().getMockParameters().get(name) != null) {
			List values = new ArrayList<String>();
			values.add(getHttpServerRequestMock().getMockParameters().get(name));
			return values;
		} else return null;
	}

	public HttpServerRequestMock getHttpServerRequestMock() {
		if(httpServerRequestMock == null) httpServerRequestMock = new HttpServerRequestMock();
		return httpServerRequestMock;
	}

	public void setHttpServerRequestMock(HttpServerRequestMock httpServerRequestMock) {
		this.httpServerRequestMock = httpServerRequestMock;
	}

	
	
}
