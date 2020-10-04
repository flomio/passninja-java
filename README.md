<p align="center">
    <img width="400px" src=https://user-images.githubusercontent.com/1587270/74537466-25c19e00-4f08-11ea-8cc9-111b6bbf86cc.png>
</p>
<h1 align="center">passninja-java</h1>
<h3 align="center">
Use <a href="https://passninja.com/docs">passninja-java</a> as a Maven package.</h3>

<div align="center">
    <a href="https://github.com/flomio/passninja-java">
        <img alt="Status" src="https://img.shields.io/badge/status-active-success.svg" />
    </a>
    <a href="https://github.com/flomio/passninja-java/issues">
        <img alt="Issues" src="https://img.shields.io/github/issues/flomio/passninja-java.svg" />
    </a>
    <a href="https://www.java.com/package/@passninja/passninja-java">
        <img alt="maven package" src="https://img.shields.io/maven/v/@passninja/passninja-java.svg?style=flat-square" />
    </a>
</div>

# Contents

- [Contents](#contents)
- [Installation](#installation)
- [Usage](#usage)
  - [`PassNinjaClient`](#passninjaclient)
  - [`PassNinjaClient Methods`](#passninjaclientmethods)
  - [Script Tag](#script-tag)
  - [Examples](#examples)
- [TypeScript support](#typescript-support)
- [Documentation](#documentation)

# Installation

Install via pip:

Include the following in your `pom.xml` for Maven:

```xml
<dependencies>
  <dependency>
    <groupId>com.github.javadev</groupId>
    <artifactId>passninja</artifactId>
    <version>1.0</version>
  </dependency>
  ...
</dependencies>
```

Gradle:

```groovy
compile 'com.github.javadev:passninja:1.0'
```

# Usage

## `Passninja`

Use this class to init a `Passninja` object. Make sure to
pass your user credentials to make any authenticated requests.

```java
import com.passninja.Passninja

account_id = '**your-account-id**'
api_key = '**your-api-key**'

Passninja.init(account_id, api_key)
```

We've placed our demo user API credentials in this example. Replace it with your
[actual API credentials](https://passninja.com/auth/profile) to test this code
through your PassNinja account and don't hesitate to contact
[PassNinja](https://passninja.com) with our built in chat system if you'd like
to subscribe and create your own custom pass type(s).

For more information on how to use `passninja-python` once it loads, please refer to
the [PassNinja JS API reference](https://passninja.com/docs/js)

## `PassNinjaClientMethods`

This library currently supports methods for creating, getting, updating, and
deleting passes via the PassNinja api. The methods are outlined below.

### Create

```java
Map<String, Object> pass = new HashMap<>();
pass.put("discount", "50%");
pass.put("memberName", "John");
PassninjaResponse<Pass> response = Pass.create("demo.coupon", /* passType */
    pass /* passData */);

System.out.println(response.getResponseBody().getUrls());
System.out.println(response.getResponseBody().getPassType());
System.out.println(response.getResponseBody().getSerialNumber());
```

### Get

```java
PassninjaResponse<Pass> response = Pass.get("demo.coupon", /* passType */
    "97694bd7-3493-4b39-b805-20e3e5e4c770" /* serialNumber */);
```

### Update

```java
Map<String, Object> pass = new HashMap<>();
pass.put("discount", "100%");
pass.put("memberName", "Ted");
PassninjaResponse<Pass> response = Pass.put("demo.coupon", /* passType */
    "97694bd7-3493-4b39-b805-20e3e5e4c770", /* serialNumber */
    pass /* passData */);
```

### Delete

```java
PassninjaResponse<Pass> response = Pass.delete("demo.coupon", /* passType */
    "97694bd7-3493-4b39-b805-20e3e5e4c770", /* serialNumber */);
System.out.println("Pass deleted. Serial_number: ", response.getResponseBody().getSerialNumber());
```

# Documentation

- [PassNinja Docs](https://passninja.com/documentation)
