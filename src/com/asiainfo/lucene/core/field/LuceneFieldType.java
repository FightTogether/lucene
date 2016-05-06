package com.asiainfo.lucene.core.field;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.lucene.index.FieldInfo;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface LuceneFieldType {
	// True if this field's value should be effective.
	public boolean effectived() default true;

	// True if this field's value should be analyzed by the Analyzer.
	public boolean tokenized() default true;

	// True if this field should be indexed (inverted)
	public boolean indexed() default false;

	// True if the field's value should be stored
	public boolean stored() default false;

	// True if this field's indexed form should be also stored into term
	// vectors.
	public boolean storeTermVectors() default false;

	// True if this field's token character offsets should also be stored into
	// term vectors.
	public boolean storeTermVectorOffsets() default false;

	public boolean storeTermVectorPositions() default false;

	// True if this field's token payloads should also be stored into the term
	// vectors.
	public boolean storeTermVectorPayloads() default false;

	// True if normalization values should be omitted for the field.
	public boolean omitNorms() default false;

	// Precision step for numeric field.
	public int numericPrecisionStep() default 4;

	public float boost() default 10.0F;

	public FieldInfo.IndexOptions indexOptions() default FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
	// public DocValues.Type docValueType();

}
