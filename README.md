# rebaser

[![Concourse](https://wings.concourse.ci/api/v1/teams/code-bandits/pipelines/rebaser/jobs/tests/badge)](https://wings.concourse.ci/teams/code-bandits/pipelines/rebaser)

[![Release](https://jitpack.io/v/codebandits/rebaser.svg)](https://jitpack.io/#codebandits/rebaser)

## Overview

A binary-to-text translating utility supporting custom sets of characters. It's similar to java.util.Base64 but you supply the set of characters which will represent the binary data.

## Usage

```java
Rebaser rebaser = new Rebaser();
char[] charset = "abc123".toCharArray();

String encodedData = rebaser.encode("Hog Island".getBytes(), charset);
System.out.println(encodedData); // bacabc11bcb1acaabacbb1a1bc1abcabbc1cbcba

byte[] decodedBytes = rebaser.decode(encodedData, charset);
System.out.println(new String(decodedBytes)); // Hog Island
```

## Installation

Add the [JitPack](https://jitpack.io/) Maven repository to your list of repositories:

```
repositories {
  ...
  maven { url 'https://jitpack.io' }
}
```

Add the compile dependency:

```
dependencies {
  ...
  compile 'com.github.codebandits:rebaser:master-SNAPSHOT'
}
```
