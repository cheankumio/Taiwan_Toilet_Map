package klapper.toilet_map;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 網路應用 權限 android.permission.INTERNET
 *
 * @author yuting-shiu Tivon
 */
public class Internet {
    /**
     * Post網路應用
     *
     * @param URL           :路徑
     * @param urlParameters :參數
     * @return ByteArrayOutputStream 可用String接
     */
    public static String fun_webPost(String URL, String urlParameters) {
        URL url;
        HttpURLConnection conn = null;
        try {
            //Create connection
            url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            conn.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            conn.setRequestProperty("Content-Language", "en-US");

            conn.setUseCaches(false);     // Post cannot use caches
            conn.setDoInput(true);        // Read from the connection. Default is true.


            // Output to the connection. Default is false, set to true because post
            // method must write something to the connection
            conn.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            reader.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
//		try {
//			HttpPost post = new HttpPost(URL);
//			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
//					HTTP.UTF_8);
//			post.setEntity(ent);
//			// 執行後一樣會傳回HttpResponse
//			HttpResponse responsePOST = new DefaultHttpClient().execute(post);
//			if (responsePOST.getStatusLine().getStatusCode() == 200) {
//				String result = EntityUtils.toString(responsePOST.getEntity());
//				return result;
//			}
//			return null;
//		} catch (Exception e) {
//			return null;
//		}
    }

    /**
     * Get網路應用
     *
     * @param URL :路徑及串好的參數
     * @return ByteArrayOutputStream 可用String接
     */
    public static String fun_webGet(String URL) {
        HttpURLConnection con = null;
        try {

            // 避免傳空格而抓不到後面的資料 Trim()函数的功能是去掉首尾空格
            URL = URL.trim().replace(" ", "%20");
            Log.d("MYLOG","URL: "+URL);
            URL url = new URL(URL);

            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(30000);//表示連線5秒還沒成功就放棄。
            con.setReadTimeout(30000);//表示讀取數據5秒還沒成功就放棄。
            con.connect();
            //Log.d("MYLOG","URL: "+url);
            int code = con.getResponseCode();
            //Log.d("MYLOG","code: "+code);
            if (code == 200) {
                // 讀入字串
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    StringBuffer sb = new StringBuffer("");
                    String NL = System.getProperty("line.separator");
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + NL);
                    }

                    //輸出
                    if (reader != null) {
                        String page = sb.toString();
                        //LogR.i("Internet.fun_webGet", "Run fun_webGet");
                        //Log.d("MYLOG","code: "+page);
                        return page;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return "";
                        }
                    }
                }
                return "";
            } else if (code == 404) {
                return "";
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }


//	public static int uploadpics(String File, String Filename) {
//		try {
//			// 設置目標
//			String actionUrl = "";
//			URL url = new URL(actionUrl);
//			// 設定規則
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			/* 允許Input、Output，使用Cache */
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			// 原本是不使用cache
//			con.setUseCaches(true);
//			/* 設置傳送的method=POST */
//			con.setRequestMethod("POST");
//			/* setRequestProperty */
//			con.setRequestProperty("Connection", "Keep-Alive");
//			con.setRequestProperty("Charset", "UTF-8");
//			// 把Content Type設為multipart/form-data
//			// 以及設定Boundary，Boundary很重要!
//			// 當你不只一個參數時，Boundary是用來區隔參數的
//			con.setRequestProperty("Content-Type",
//					"multipart/form-data;boundary=*****");
//
//			/* 設置DataOutputStream 設定寫入資料 */
//			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//			ds.writeBytes("--" + "*****" + "\r\n");
//			// Newname包含案件編碼跟今日日期傳過去後再拆開案件編碼當目錄名稱
//			ds.writeBytes("Content-Disposition: form-data; "
//					+ "name=\"photofile\";filename=\"" + Filename + "\""
//					+ "\r\n");
//			ds.writeBytes("Content-Type: image/jpeg\r\n");// -----
//			ds.writeBytes("\r\n");
//			/* 取得文件的FileInputStream */
//			File uploadFile = new File(File);
//			FileInputStream fStream = new FileInputStream(uploadFile);
//			/* 設置每次寫入2048bytes 原本是1024 */
//			int bufferSize = 2048;
//			byte[] buffer = new byte[bufferSize];
//			int length = -1;
//			/* 從文件讀取數據至緩衝區 */
//			while ((length = fStream.read(buffer)) != -1) {
//				/* 將資料寫入DataOutputStream中 */
//				ds.write(buffer, 0, length);
//			}
//			ds.writeBytes("\r\n");
//			ds.writeBytes("--*****--\r\n");
//			/* close streams */
//			fStream.close();
//			ds.flush();
//			ds.close();
//			/* 取得Response內容 */
//			InputStream is = con.getInputStream();
//			int ch;
//			StringBuffer b = new StringBuffer();
//			while ((ch = is.read()) != -1) {
//				b.append((char) ch);
//			}
//			Log.i("success", "照片檔上傳成功");
//			return 6;
//		} catch (Exception e) {
//			Log.i("fail", "上傳照片失敗" + e.toString());
//			return 8;
//		}
//	}

//	public static int uploadpicsSteering(String File, String Filename) {
//		try {
//			// 設置目標
//			String actionUrl = "";
//			URL url = new URL(actionUrl);
//			// 設定規則
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			/* 允許Input、Output，使用Cache */
//			con.setDoInput(true);
//			con.setDoOutput(true);
//			// 原本是不使用cache
//			con.setUseCaches(true);
//			/* 設置傳送的method=POST */
//			con.setRequestMethod("POST");
//			/* setRequestProperty */
//			con.setRequestProperty("Connection", "Keep-Alive");
//			con.setRequestProperty("Charset", "UTF-8");
//			// 把Content Type設為multipart/form-data
//			// 以及設定Boundary，Boundary很重要!
//			// 當你不只一個參數時，Boundary是用來區隔參數的
//			con.setRequestProperty("Content-Type",
//					"multipart/form-data;boundary=*****");
//
//			/* 設置DataOutputStream 設定寫入資料 */
//			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
//			ds.writeBytes("--" + "*****" + "\r\n");
//			// Newname包含案件編碼跟今日日期傳過去後再拆開案件編碼當目錄名稱
//			ds.writeBytes("Content-Disposition: form-data; "
//					+ "name=\"photofile\";filename=\"" + Filename + "\""
//					+ "\r\n");
//			ds.writeBytes("Content-Type: image/jpeg\r\n");// -----
//			ds.writeBytes("\r\n");
//			/* 取得文件的FileInputStream */
//			File uploadFile = new File(File);
//			FileInputStream fStream = new FileInputStream(uploadFile);
//			/* 設置每次寫入2048bytes 原本是1024 */
//			int bufferSize = 2048;
//			byte[] buffer = new byte[bufferSize];
//			int length = -1;
//			/* 從文件讀取數據至緩衝區 */
//			while ((length = fStream.read(buffer)) != -1) {
//				/* 將資料寫入DataOutputStream中 */
//				ds.write(buffer, 0, length);
//			}
//			ds.writeBytes("\r\n");
//			ds.writeBytes("--*****--\r\n");
//			/* close streams */
//			fStream.close();
//			ds.flush();
//			ds.close();
//			/* 取得Response內容 */
//			InputStream is = con.getInputStream();
//			int ch;
//			StringBuffer b = new StringBuffer();
//			while ((ch = is.read()) != -1) {
//				b.append((char) ch);
//			}
//			Log.i("success", "照片檔上傳成功");
//			return 6;
//		} catch (Exception e) {
//			Log.i("fail", "上傳照片失敗" + e.toString());
//			return 8;
//		}
//	}

// 使用 Cookie 的方式：

// CookieStore cookieStore = new BasicCookieStore();
//	String get_url_contents(String url, List<NameValuePair> params,
//			CookieStore cookieStore) {
//		try {
//			HttpClient client = new DefaultHttpClient();
//			HttpResponse response = null;
//			if (cookieStore == null)
//				response = client.execute(new HttpGet(params == null
//						|| params.size() == 0 ? url : url + "?"
//						+ URLEncodedUtils.format(params, "utf-8")));
//			else {
//				HttpContext mHttpContext = new BasicHttpContext();
//				mHttpContext.setAttribute(ClientContext.COOKIE_STORE,
//						cookieStore);
//				response = client.execute(
//						new HttpGet(params == null || params.size() == 0 ? url
//								: url
//										+ "?"
//										+ URLEncodedUtils.format(params,
//												"utf-8")), mHttpContext);
//			}
//			HttpEntity result = response.getEntity();
//			if (result != null) {
//				// return EntityUtils.toString(result); // 有編碼問題
//				InputStream mInputStream = result.getContent();
//				String out = getStringFromInputStream(mInputStream);
//				mInputStream.close();
//				return out;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	String getStringFromInputStream(InputStream in) {
//		byte[] data = new byte[1024];
//		int length;
//		if (in == null)
//			return null;
//		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
//		try {
//			while ((length = in.read(data)) != -1)
//				mByteArrayOutputStream.write(data, 0, length);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return new String(mByteArrayOutputStream.toByteArray());
//	}
}
// HTTP 的回應代碼是三位數字，其中第一個數字定義了代碼的類別:
// 範圍 類別 含義
// 100～199 訊息 請求被接收，正在處理
// 200～299 成功 動作被成功接收、理解和接受
// 300～399 重新導向 必須採取其他動作才能完成請求
// 400～499 用戶錯誤 請求包含不良語法或無法完成
// 500～599 伺服器錯誤 伺服器無法處理的請求
// 其中較常見的 HTTP 回應代碼有:
// 200: 請求成功
// 400: 語法錯誤
// 401: 未經授權
// 403: 禁止訪問
// 404: 請求的網頁不存在
// 500: 伺服器錯誤
//
// 較為完整的有:
//
// 1xx: Informational - Request received, continuing process
// - 100 - Continue
// - 101 - Switching Protocols
// 2xx: Success - The action was successfully received, understood, and
// accepted
// - 200 - OK
// - 201 - Created
// - 202 - Accepted
// - 203 - Non-Authoritative Information
// - 204 - No Content
// - 205 - Reset Content
// - 206 - Partial Content
// 3xx: Redirection - Further action must be taken in order to complete the
// request
// - 300 - Multiple Choices
// - 301 - Moved Permanently
// - 302 - Moved Temporarily
// - 303 - See Other
// - 304 - Not Modified
// - 305 - Use Proxy
// 4xx: Client Error - The request contains bad syntax or cannot be
// fulfilled - translation: "you blew it."
// - 400 - Bad Request
// - 401 - Unauthorized
// - 402 - Payment Required
// - 403 - Forbidden
// - 404 - Not Found
// - 405 - Method Not Allowed
// - 406 - Not Acceptable
// - 407 - Proxy Authentication Required
// - 408 - Request Time-out
// - 409 - Conflict
// - 410 - Gone
// - 411 - Length Required
// - 412 - Precondition Failed
// - 413 - Request Entity Too Large
// - 414 - Request-URI Too Large
// - 415 - Unsupported Media Type
// 5xx: Server Error - The server failed to fulfill an apparently valid
// request - translation: "the server blew it."
// - 500 - Internal Server Error
// - 501 - Not Implemented
// - 502 - Bad Gateway
// - 503 - Service Unavailable
// - 504 - Gateway Time-out
// - 505 - HTTP Version not supported