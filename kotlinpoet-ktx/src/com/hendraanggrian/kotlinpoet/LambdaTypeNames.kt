package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName

/** Returns a [LambdaTypeName], applying `parameters` to `this`.  */
fun TypeName?.lambdaBy(parameters: List<ParameterSpec>, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, parameters, returnType)

/** Returns a [LambdaTypeName], applying `parameters` to `this`.  */
fun TypeName?.lambdaBy(vararg parameters: TypeName, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returnType)

/** Returns a [LambdaTypeName], applying `parameters` to `this`.  */
fun TypeName?.lambdaBy(vararg parameters: ParameterSpec, returnType: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returnType)

/** Create a nullable coppy of [LambdaTypeName].  */
fun LambdaTypeName.asNullable(): LambdaTypeName =
    copy(true)
