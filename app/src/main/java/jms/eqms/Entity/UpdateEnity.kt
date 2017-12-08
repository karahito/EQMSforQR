package jms.eqms.Entity

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

/**
 *  更新用データクラス
 *
 *  @param code             備品固有番号{@code nchar(5)}
 *  @param update_date      更新日時(yyyyMMdd){@code nchar(8)}
 *  @param modified_by      更新者{@code nchar(3)}
 *  @author D.Noguchi
 *  @since 1,Sep.2017
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
data class UpdateEnity(
        var code:String,
        var update_date:String,
        var modified_by:String
)