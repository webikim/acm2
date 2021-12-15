package io.cloud4u.lib.acm.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageVO {
	String resourceName;
	Object imageSource;
	String contentType;
}
