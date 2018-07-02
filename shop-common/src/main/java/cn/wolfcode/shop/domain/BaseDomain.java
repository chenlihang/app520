package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by wolfcode on 2018/03/29 0029.
 */
@Setter
@Getter
public class BaseDomain implements Serializable{

    protected Long id;
}
