package jms.eqms.Entity

/**
 * Created by jmsbusinesssoftmac on 2017/12/12.
 */
enum class UpdateStatus(val value:Int){
    YET(0),
    ALREADY(1),
    CALLBACK_WAIT(2)
}