package cn.zjc.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author ZJC
 * @version 2016-5-7
 */
public class HttpUtil {

	private static final Logger log = LogManager.getLogger(HttpUtil.class);

	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 5000;
	private static final String DEFULT_CHARSET = "UTF-8";

	static {
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		requestConfig = configBuilder.build();
	}

	private static List<NameValuePair> paramsHandler(Map<String, String> params) {
		if (params != null && params.size() > 0) {
			List<NameValuePair> paramsList = new ArrayList<>();
			for (Map.Entry<String, String> param : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
				paramsList.add(pair);
			}
			return paramsList;
		}
		return null;
	}

	/**
	 * POST http请求方法(无参数)
	 *
	 * @param url
	 * @return
	 */
	public static String post(String url) {
		return post(url, null, null);
	}

	/**
	 * POST http请求方法
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params) {
		return post(url, null, params);
	}

	/**
	 * POST http请求方法（设置请求头）
	 *
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		int statusCode;
		CloseableHttpResponse response = null;
		String responseBody = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			//设置请求头
			if (headers == null) {
				httpPost.setHeader("Content-Type",
						"application/x-www-form-urlencoded; charset=" + DEFULT_CHARSET);
			} else {
				if (headers.size() > 0) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						httpPost.setHeader(header.getKey(), header.getValue());
					}
				}
			}
			//设置请求参数
			if (params != null) {
				HttpEntity requestEntity = EntityBuilder
						.create()
						.setParameters(paramsHandler(params))
						.build();
				httpPost.setEntity(requestEntity);
			}

			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity responseEntity = response.getEntity();
				if (null != responseEntity) {
					responseBody = EntityUtils.toString(responseEntity, DEFULT_CHARSET);
				}
				EntityUtils.consume(responseEntity);
			}
			return responseBody;
		} catch (Exception e) {
			log.error("msg--->" + e.getMessage());
			return null;
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}

			if (null != httpclient) {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * POST http请求方法（参数为json格式）
	 *
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public static String post(String url, Object jsonParams) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		int statusCode;
		CloseableHttpResponse response = null;
		String responseBody = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			//设置请求参数,json格式
			if (jsonParams != null) {
				StringEntity stringEntity = new StringEntity(jsonParams.toString());
				stringEntity.setContentEncoding(DEFULT_CHARSET);
				stringEntity.setContentType("application/json");
				httpPost.setEntity(stringEntity);
			}

			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity responseEntity = response.getEntity();
				if (null != responseEntity) {
					responseBody = EntityUtils.toString(responseEntity, DEFULT_CHARSET);
				}
				EntityUtils.consume(responseEntity);
			}
			return responseBody;
		} catch (Exception e) {
			log.error("msg--->" + e.getMessage());
			return null;
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}

			if (null != httpclient) {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * GET http请求方法（无参数）
	 *
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		return get(url, null, null);
	}

	/**
	 * GET http请求方法
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, String> params) {
		return get(url, null, params);
	}

	/**
	 * GET http请求方法（设置请求头）
	 *
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuilder uri = new StringBuilder();
		uri.append(url);
		int statusCode;
		CloseableHttpResponse response = null;
		String responseBody = null;
		try {

			//设置请求参数
			if (params != null && params.size() > 0) {
				uri.append("?");
				for (Map.Entry<String, String> param : params.entrySet()) {
					uri.append(param.getKey()).append("=").append(param.getValue()).append("&");
				}
			}

			HttpGet httpGet = new HttpGet(uri.toString());
			httpGet.setConfig(requestConfig);

			//设置请求头
			if (headers == null) {
				httpGet.setHeader("Content-Type",
						"application/x-www-form-urlencoded; charset=" + DEFULT_CHARSET);
			} else {
				if (headers.size() > 0) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						httpGet.setHeader(header.getKey(), header.getValue());
					}
				}
			}

			response = httpclient.execute(httpGet);
			statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity responseEntity = response.getEntity();
				if (null != responseEntity) {
					responseBody = EntityUtils.toString(responseEntity, DEFULT_CHARSET);
				}
				EntityUtils.consume(responseEntity);
			}
			return responseBody;
		} catch (Exception e) {
			log.error("msg--->" + e.getMessage());
			return null;
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}

			if (null != httpclient) {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
		SSLConnectionSocketFactory socketFactory = null;
		//重写校验方法，取消SSL校验
		TrustManager manager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{manager}, null);
			socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		} catch (Exception e) {
			log.error("msg--->" + e.getMessage());
			return null;
		}
		return socketFactory;
	}


	/**
	 * https POST请求，不进行SSL校验，参数为K-V格式
	 *
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String postHttps(String url, Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(createSSLConnectionSocketFactory())
				.setDefaultRequestConfig(requestConfig).build();
		int statusCode;
		CloseableHttpResponse response = null;
		String responseBody = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			//设置请求头
			if (headers == null) {
				httpPost.setHeader("Content-Type",
						"application/x-www-form-urlencoded; charset=" + DEFULT_CHARSET);
			} else {
				if (headers.size() > 0) {
					for (Map.Entry<String, String> header : headers.entrySet()) {
						httpPost.setHeader(header.getKey(), header.getValue());
					}
				}
			}
			//设置请求参数
			if (params != null) {
				HttpEntity requestEntity = EntityBuilder
						.create()
						.setParameters(paramsHandler(params))
						.build();
				httpPost.setEntity(requestEntity);
			}

			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity responseEntity = response.getEntity();
				if (null != responseEntity) {
					responseBody = EntityUtils.toString(responseEntity, DEFULT_CHARSET);
				}
				EntityUtils.consume(responseEntity);
			}
			return responseBody;
		} catch (Exception e) {
			log.error("msg--->" + e.getMessage());
			return null;
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}

			if (null != httpclient) {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * POST https请求方法，不进行SSL校验（参数为json格式）
	 *
	 * @param url
	 * @param jsonParams
	 * @return
	 */
	public static String postHttps(String url, Object jsonParams) {
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(createSSLConnectionSocketFactory())
				.setDefaultRequestConfig(requestConfig).build();
		int statusCode;
		CloseableHttpResponse response = null;
		String responseBody = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			//设置请求参数,json格式
			if (jsonParams != null) {
				StringEntity stringEntity = new StringEntity(jsonParams.toString());
				stringEntity.setContentEncoding(DEFULT_CHARSET);
				stringEntity.setContentType("application/json");
				httpPost.setEntity(stringEntity);
			}

			response = httpclient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == statusCode) {
				HttpEntity responseEntity = response.getEntity();
				if (null != responseEntity) {
					responseBody = EntityUtils.toString(responseEntity, DEFULT_CHARSET);
				}
				EntityUtils.consume(responseEntity);
			}
			return responseBody;
		} catch (Exception e) {
			log.error("msg--->" + e.getMessage());
			return null;
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}

			if (null != httpclient) {
				try {
					httpclient.close();
				} catch (IOException e) {
					log.error("msg--->" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
