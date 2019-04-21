package com.chinamcloud.api;

import com.fasterxml.jackson.annotation.JsonInclude;

//import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 返回结果的封装
 * @author porterxie
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeResult {

	public static enum Code{
		Success(0),Failed(1),Invalid(1000),SuccessWithWarnning(-1),LoginTimeOut(2),AnalysisRunning(-2);
		Code(int code){
			this.code = code;
		}
		private int code;
		
		public int getCodeValue(){
			return this.code;
		}
	}
	private int code;
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	private Object result;
	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public CodeResult(Code code, Object result) {
		this.code = code.getCodeValue();
		this.result = result;
	}
	
	public CodeResult(Code code, Object result, String msg) {
		this.code = code.getCodeValue();
		this.result = result;
		this.msg = msg;
	}
	
	public CodeResult(Code code, String msg) {
		this.code = code.getCodeValue();
		this.msg = msg;
	}
	
	public CodeResult(Code code) {
		this.code = code.getCodeValue();
	}
	
	
	public static CodeResult failedResult(String msg){
		return new CodeResult(Code.Failed,msg);
	}

	public static CodeResult successResult(String msg, Object result){
		return new CodeResult(Code.Success, result, msg);
	}
	
	public static CodeResult successResult(){
		return CodeResult.successResult(null,null);
	}
	
	
}
