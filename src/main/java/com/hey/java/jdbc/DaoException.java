package com.hey.java.jdbc;

/**
* 这个类很重要，即完成了抛异常的动作、通过编译，又可以使数据逻辑层的接口保持简洁。
*
* @author 博客园-给最苦
* @version V17.11.08
*/
public class DaoException extends RuntimeException {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   public DaoException() {
       // TODO Auto-generated constructor stub
   }

   public DaoException(String message) {
       super(message);
       // TODO Auto-generated constructor stub
   }

   public DaoException(Throwable cause) {
       super(cause);
       // TODO Auto-generated constructor stub
   }

   public DaoException(String message, Throwable cause) {
       super(message, cause);
       // TODO Auto-generated constructor stub
   }

   public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
       super(message, cause, enableSuppression, writableStackTrace);
       // TODO Auto-generated constructor stub
   }

}