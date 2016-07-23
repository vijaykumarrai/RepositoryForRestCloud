package com.lessonslab.cxfrestservice.internal;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuthService;
import com.lessonslab.cxfrestservice.CxfRestServiceCloud;
import com.lessonslab.cxfrestservice.dao.EmployeeDao;



public class CxfRestServiceCloudImpl implements CxfRestServiceCloud 
{
	@Autowired
	private EmployeeDao employeeDao; 

	@Override
	public Response getEmployeeDetail(String employeeId) 
	{
		if(employeeId == null)
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}		
		return Response.ok(employeeDao.getEmployeeDetails(employeeId)).build();
	}
	
	
	@Override
	public Response getNambiDetail(String employeeId) 
	{
		/*OAuthService service = new ServiceBuilder()
                .apiKey(YOUR_API_KEY)
                .apiSecret(YOUR_API_SECRET)
                .build(LinkedInApi20.instance());*/
		
		//final 
		OAuth10aService service = new ServiceBuilder()
                .apiKey("j1NycVSb3OzeJnFRpD5a6VDl4")
                .apiSecret("q3adCeSoP9zpIzdJ0nzWPsonTWeIsYA85aBRkWj8kgeNoz7KBK")
                .callback("oob")
                .build(TwitterApi.instance());
		
		//final 
		OAuth1RequestToken requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);
		final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, "2347154150");//"2347154150-bjKFtxGySUAd5hqhRtaDX82g0kXXQBkdrhd4KKN");//"VijayRai23t");
				//"https://api.twitter.com/oauth/access_token");//"verifier you got from the user/callback");
		
		final OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json", service);
		service.signRequest(accessToken, request); // the access token from step 4
		final com.github.scribejava.core.model.Response response = request.send();
		System.out.println(response.getBody());
		
		if(employeeId == null)
		{
			return Response.status(Response.Status.BAD_REQUEST).build();
		}		
		return Response.ok(employeeDao.getEmployeeDetails(employeeId)).build();
	}
}
