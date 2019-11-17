package com.ddd.filter;

import com.sun.xml.internal.ws.client.sei.SEIStub;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/*
* 敏感词汇过滤器
* */
@WebFilter("/*")
public class SensitiveWordFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
          //创建代理对象，增强Parameter方法
        ServletRequest  req_proxy= (ServletRequest )Proxy.newProxyInstance(req.getClass().getClassLoader(), req.getClass().getInterfaces(), new InvocationHandler() {
            @Override
          public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //增强Parameter方法
                if(method.getName().equals("getParameter")){
                    //增强返回值
                    //获取返回值
                   String value= (String) method.invoke(req,args);
                        if(value!=null){
                            for (String str : list) {
                                    value=value.replaceAll(str,"***");

                            }
                        }
                        return  value;
                }
                    if(method.getName().equals("getParameterMap")) {
                       /* System.out.println("我是getParameterMap");
                      Map<String,String[]> map= (Map<String, String[]>) method.invoke(req,args);
                      if(map!=null||!"".equals(map)||map.size()>0){
                          Set<String> keyset = map.keySet();
                          for (String  key: keyset) {
                              String[] value = map.get(key);
                              for (String str : list) {
                                  if(value[0].contains(str)){
                                      value[0]= value[0].replaceAll(str, "***");
                                  }
                              }
                          }
                      }
                      return map;*/
                        Map<String,String[]> map= (Map<String, String[]>) method.invoke(req,args);
                        Set<String> keys = map.keySet();
                        Map<String, Object> newMap = new HashMap<>();
                        for (String key : keys) {
                            String[] values = map.get(key);
                            List<String> sb = new ArrayList<>();
                            for (String value : values) {
                                for (String str : list) {
                                    value = value.replaceAll(str, "***");
                                }
                                sb.add(value);
                            }
                            String[] sBuffer = new String[values.length];
                            String[] sb_string = sb.toArray(sBuffer);
                            newMap.put(key, sb_string);
                        }

                        return newMap;
                    }
                if (method.equals("getParameterValues")) {
                    String[] values = (String[]) method.invoke(req, args);
                    List<String> sb = new ArrayList<>();
                    if (values != null) {
                        for (String value : values) {
                            for (String str : list) {
                                value = value.replaceAll(str, "***");
                            }
                            sb.add(value);
                        }
                    }
                    String[] sBuffer = new String[values.length];
                    String[] sb_string = sb.toArray(sBuffer);
                    return sb_string;
                }
                return method.invoke(req,args);
            }
        });
        //放行
        chain.doFilter(req_proxy, resp);
    }
  private List<String> list=new ArrayList<String>();//存放敏感词汇
    public void init(FilterConfig config) throws ServletException {
        try {
            //加载文件
            //获取文件的真实路径
            ServletContext servletContext = config.getServletContext();
            String realPath = servletContext.getRealPath("/WEB-INF/classes/敏感词汇.txt");
            // 读取文件
            BufferedReader bf=new BufferedReader(new FileReader(realPath));
            //将文件的每一行添加到list中
            String line=null;
            while ((line=bf.readLine())!=null){
                list.add(line);
            }
            bf.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(list);
    }

}
