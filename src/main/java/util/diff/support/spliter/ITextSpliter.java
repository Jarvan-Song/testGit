package util.diff.support.spliter;

import java.io.Serializable;

/**
 * Created by songpanfei on 2018/11/19.
 */
public interface ITextSpliter extends Serializable{
	String[] split(String text);
}