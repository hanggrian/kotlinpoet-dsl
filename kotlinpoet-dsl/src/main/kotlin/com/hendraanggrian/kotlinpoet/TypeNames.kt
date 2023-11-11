package com.hendraanggrian.kotlinpoet

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.DelicateKotlinPoetApi
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import java.lang.reflect.Type
import kotlin.reflect.KClass

inline val TypeName.nullable: TypeName get() = copy(true)

@DelicateKotlinPoetApi(DELICATE_API)
inline val Class<*>.name2: ClassName get() = asClassName()

@DelicateKotlinPoetApi(DELICATE_API)
inline val KClass<*>.javaName: ClassName get() = java.asClassName()

inline val KClass<*>.name: ClassName get() = asClassName()

/** Returns a new class name instance for the given fully-qualified class name string. */
inline fun classNamed(fullName: String): ClassName = ClassName.bestGuess(fullName)

/** Returns a class name created from the given parts. */
inline fun classNamed(
    packageName: String,
    simpleName: String,
    vararg simpleNames: String,
): ClassName = ClassName(packageName, simpleName, *simpleNames)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun lambdaTypeNamed(vararg parameters: TypeName, returns: TypeName): LambdaTypeName =
    LambdaTypeName.get(null, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun lambdaTypeNamed(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: TypeName,
): LambdaTypeName = LambdaTypeName.get(null, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(vararg parameters: TypeName, returns: TypeName): LambdaTypeName =
    LambdaTypeName.get(this, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
fun TypeName?.lambdaBy(vararg parameters: Class<*>, returns: Class<*>): LambdaTypeName =
    LambdaTypeName.get(
        this,
        *parameters.map { it.name2 }.toTypedArray(),
        returnType = returns.name2,
    )

/** Returns a lambda type with returnType and parameters listed in parameters. */
fun TypeName?.lambdaBy(vararg parameters: KClass<*>, returns: KClass<*>): LambdaTypeName =
    LambdaTypeName.get(
        this,
        *parameters.map { it.name }.toTypedArray(),
        returnType = returns.name,
    )

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: TypeName,
): LambdaTypeName = LambdaTypeName.get(this, *parameters, returnType = returns)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: Class<*>,
): LambdaTypeName = LambdaTypeName.get(this, *parameters, returnType = returns.name2)

/** Returns a lambda type with returnType and parameters listed in parameters. */
inline fun TypeName?.lambdaBy(
    vararg parameters: ParameterSpec = emptyArray(),
    returns: KClass<*>,
): LambdaTypeName = LambdaTypeName.get(this, *parameters, returnType = returns.name)

/** Returns a parameterized type, applying typeArguments to rawType. */
inline fun <reified T> ClassName.parameterizedBy(): ParameterizedTypeName =
    parameterizedBy(T::class.name)

/** Returns type variable named name without bounds. */
inline val String.generics: TypeVariableName get() = TypeVariableName(this)

/** Returns type variable named name with variance and bounds. */
inline fun String.genericsBy(
    vararg bounds: TypeName,
    variance: KModifier? = null,
): TypeVariableName = TypeVariableName(this, *bounds, variance = variance)

/** Returns type variable named name with variance and bounds. */
inline fun String.genericsBy(vararg bounds: Type, variance: KModifier? = null): TypeVariableName =
    TypeVariableName(this, *bounds, variance = variance)

/** Returns type variable named name with variance and bounds. */
inline fun String.genericsBy(
    vararg bounds: KClass<*>,
    variance: KModifier? = null,
): TypeVariableName = TypeVariableName(this, *bounds, variance = variance)

/** Returns a type that represents an unknown type that produces outType. */
inline val TypeName.producer: WildcardTypeName get() = WildcardTypeName.producerOf(this)

/** Returns a type that represents an unknown type that consumes inType. */
inline val TypeName.consumer: WildcardTypeName get() = WildcardTypeName.consumerOf(this)
