package firok.hfs;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回数据格式
 * @since 2020-03-24
 */
@Data
public class Ret<T> implements Serializable {

    private static final long serialVersionUID = -3072176462681346541L;

    /**
     * 请求成功
     */
    public static final int SUCCESS = 0;

    /**
     * 请求失败
     */
    public static final int FAIL = 1;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;


    public Ret() {
        super();
    }

    public Ret(Integer code) {
        super();
        this.code = code;
    }

    public Ret(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Ret(Integer code, Throwable throwable) {
        super();
        this.code = code;
        this.message = throwable.getMessage();
    }

    public Ret(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    public Ret(Integer code, String message, T data) {
        super();
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 不带返回数据的成功响应
     * @return
     */
    public static Ret<?> success(){
        return new Ret<>(SUCCESS, "请求成功");
    }

    /**
     * 带返回数据的成功响应
     * @param data
     * @return
     */
    public static <T> Ret<T> success(T data){
        return new Ret<>(SUCCESS, "请求成功", data);
    }

    /**
     * 不带返回消息的失败响应
     * @return
     */
    public static Ret<?> fail(){
        return new Ret<>(FAIL, "请求失败");
    }

    /**
     * 带返回消息的失败响应
     * @param message
     * @return
     */
    public static Ret<?> fail(String message){
        return new Ret<>(FAIL, message);
    }

}
