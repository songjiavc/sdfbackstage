package com.sdf.manager.common.enums;

/** 
  * @ClassName: BizExceptionEnum 
  * @Description: 存放异常信息 
  * @author songj@sdfcp.com
  * @date 2015年10月15日 下午1:40:45 
  *  
  */
public enum BizExceptionEnum {
    //所有业务异常描述放在这里
    SDF_USER_REPEAT_CODE(0101, "用户编码冲突!"),
	SDF_USER_DELETE_NOT_EXIST(0102, "没有要删除的帐号或是帐号已经被删除!");
    // 成员变量  
    private int index;  
    private String name;  
    // 构造方法  
    private BizExceptionEnum(int index,String name) {  
        this.index = index;  
        this.name = name;  
    }
    // 构造方法  
    private BizExceptionEnum(BizExceptionEnum bizExceptionEnum) {  
        this.index = bizExceptionEnum.getIndex();  
        this.name = bizExceptionEnum.getName();  
    }
    //覆盖方法  
    @Override  
    public String toString() {  
        return this.index+"_"+this.name;  
    }
    
    private int getIndex(){
        return this.index;
    }
    private String getName(){
        return this.name();
    }
    
    public static String getName(int index) {
        for (BizExceptionEnum temp : BizExceptionEnum.values()) {
            if (temp.getIndex() == index) {
                return temp.name;
            }
        }
        return null;
    }
}  