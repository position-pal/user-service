## [0.4.0](https://github.com/position-pal/user-service/compare/0.3.0...0.4.0) (2024-11-18)

### Features

* implemented adapter with rabbitmq for sending updates regarding groups ([#83](https://github.com/position-pal/user-service/issues/83)) ([b685875](https://github.com/position-pal/user-service/commit/b685875241879a1ce60b03879fc252bb474230ec))

### Dependency updates

* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.112 ([c00b1aa](https://github.com/position-pal/user-service/commit/c00b1aa7b7857dfd156c864e2d8899f83800ea6c))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.113 ([4aa6061](https://github.com/position-pal/user-service/commit/4aa606165a33f30fa4a126f4cdc43f76bf3bfd39))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.114 ([340b045](https://github.com/position-pal/user-service/commit/340b0455b0a41b9ec529d73d45649867b8c565a3))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.115 ([#80](https://github.com/position-pal/user-service/issues/80)) ([4ac2c09](https://github.com/position-pal/user-service/commit/4ac2c09deeee204e970c676319b418db09cf57d2))
* **deps:** update grpc-java monorepo to v1.68.1 ([6050831](https://github.com/position-pal/user-service/commit/6050831a5fa4fbad59aeb23abdc595bacfacabac))
* **deps:** update ktor monorepo to v3.0.1 ([256ff81](https://github.com/position-pal/user-service/commit/256ff81c79a75994e3d58934aea2282b48eee0bb))
* **deps:** update node.js to v22 ([d0a2932](https://github.com/position-pal/user-service/commit/d0a293214abb15f6915fcdd7f557eff750188d96))
* **deps:** update plugin kotlin-qa to v0.69.0 ([a63f097](https://github.com/position-pal/user-service/commit/a63f09726106ddab5d212784dfba1b3a9c320733))
* **deps:** update postgres docker tag to v17.1 ([#82](https://github.com/position-pal/user-service/issues/82)) ([eed31f9](https://github.com/position-pal/user-service/commit/eed31f95361955c0ad9cc184e8143be2e74a4c2e))
* **deps:** update postgres:17.0-alpine docker digest to 3d5a606 ([#78](https://github.com/position-pal/user-service/issues/78)) ([83c1dcd](https://github.com/position-pal/user-service/commit/83c1dcd956e9841b3efbc55f540394ba420f4af8))
* **deps:** update postgres:17.0-alpine docker digest to 72ff2e0 ([#77](https://github.com/position-pal/user-service/issues/77)) ([8e542cf](https://github.com/position-pal/user-service/commit/8e542cfc5585efa05170a0ad29a6a330294a90fc))
* **deps:** update proto to v4.28.3 ([39f5863](https://github.com/position-pal/user-service/commit/39f5863386c4177de813739c5730f7c889447971))

### Build and continuous integration

* **deps:** update actions/checkout digest to 11bd719 ([21cbb34](https://github.com/position-pal/user-service/commit/21cbb341ae5dcf37560e5c194f02b8646f1f5ae7))
* **deps:** update actions/setup-java digest to 8df1039 ([d159726](https://github.com/position-pal/user-service/commit/d159726dc21b5706f8a76e50a028975effc73df1))
* **deps:** update actions/setup-node action to v4.1.0 ([4bca0fa](https://github.com/position-pal/user-service/commit/4bca0faa7742e26767c61e1b2c9b27e6e87e4f8f))

## [0.3.0](https://github.com/position-pal/user-service/compare/0.2.0...0.3.0) (2024-10-22)

### Features

* **auth:** implemented authentication with jwt ([#65](https://github.com/position-pal/user-service/issues/65)) ([c1e5c1a](https://github.com/position-pal/user-service/commit/c1e5c1a57eea1923215fbe8cf97c81edb77d33e3))

### Dependency updates

* **deps:** pin postgres docker tag to 578ca5c ([e7fcc26](https://github.com/position-pal/user-service/commit/e7fcc2670d6432100466d3149efa5bbadea33d2f))
* **deps:** update dependency io.github.cdimascio:dotenv-kotlin to v6.4.2 ([ea5c4e5](https://github.com/position-pal/user-service/commit/ea5c4e5a6645b89f79c357b737135dd6210895d4))
* **deps:** update dependency io.mockk:mockk to v1.12.8 ([c561403](https://github.com/position-pal/user-service/commit/c561403d387b640d820e435d740580f8d139065b))
* **deps:** update dependency io.mockk:mockk to v1.13.13 ([02eda35](https://github.com/position-pal/user-service/commit/02eda355f696bcb370f8558833084799581a4dac))
* **deps:** update dependency org.postgresql:postgresql to v42.7.4 ([af3fd15](https://github.com/position-pal/user-service/commit/af3fd1550ef3505fc6969956198bb66f62d1137b))
* **deps:** update ktorm to v3.6.0 ([c65bfe7](https://github.com/position-pal/user-service/commit/c65bfe79b4731df4119d36aa71ff981c447bffed))
* **deps:** update ktorm to v4 ([e54b84c](https://github.com/position-pal/user-service/commit/e54b84cf052664646a791cee9448cbd08ad19c50))
* **deps:** update postgres docker tag to v14.13 ([45f7009](https://github.com/position-pal/user-service/commit/45f700920655d5ed605851b9cbf9a7812c51e231))
* **deps:** update postgres docker tag to v17 ([7c96708](https://github.com/position-pal/user-service/commit/7c967087e6304fdda972f6e54ec6d692657a8783))

## [0.2.0](https://github.com/position-pal/user-service/compare/v0.1.0...0.2.0) (2024-10-17)

### Features

* **postgres:** adding repositories for managing users and groups using a postgres db ([#49](https://github.com/position-pal/user-service/issues/49)) ([929299f](https://github.com/position-pal/user-service/commit/929299fd9bd9dbf40197d8e0fd2adfc37101938b))

### Dependency updates

* **deps:** update dependency org.jetbrains.kotlin.jvm to v2.0.21 ([0ac2815](https://github.com/position-pal/user-service/commit/0ac28155c915ee22fe2fadc57fc34c84d3bee31e))
* **deps:** update dependency org.mockito:mockito-core to v5.14.0 ([fbd7c48](https://github.com/position-pal/user-service/commit/fbd7c48ebd215c4a1c0f825383c4bcd15c641f76))
* **deps:** update dependency org.mockito:mockito-core to v5.14.1 ([57ec479](https://github.com/position-pal/user-service/commit/57ec4799135c4a6d4edfe1bebecaf78f2ba5d100))
* **deps:** update dependency org.mockito:mockito-core to v5.14.2 ([97a3681](https://github.com/position-pal/user-service/commit/97a3681e1d8fbd5189ccc1cecc0784fe50924bea))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.108 ([cc24ceb](https://github.com/position-pal/user-service/commit/cc24ceb6232a3d883969b143277266d4b770a931))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.109 ([3322a99](https://github.com/position-pal/user-service/commit/3322a99ff2d361e8c0f843b6b8292baca16b2f5e))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.110 ([485cb4f](https://github.com/position-pal/user-service/commit/485cb4fcfffe454ca43d1c8eb23081ddd3f26f94))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.111 ([1cf6956](https://github.com/position-pal/user-service/commit/1cf6956442ee770ffe968b24c9e040baa1da4cdb))
* **deps:** update node.js to 20.18 ([7f2a080](https://github.com/position-pal/user-service/commit/7f2a080cbf54110f21bc2af90107368a7bc73a68))
* **deps:** update plugin kotlin-qa to v0.67.3 ([b0276fa](https://github.com/position-pal/user-service/commit/b0276fac01912110b390ace1604126e6d1c1022d))
* **deps:** update plugin kotlin-qa to v0.68.0 ([922f809](https://github.com/position-pal/user-service/commit/922f8091b87530cd1b175686fe370701e449898f))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v2.0.13 ([952df7d](https://github.com/position-pal/user-service/commit/952df7d00c9475dd73f3a375d6c2fd5680dc4805))

### Build and continuous integration

* **deps:** update actions/checkout digest to eef6144 ([cb71030](https://github.com/position-pal/user-service/commit/cb7103064556670852afdd2d20b18b2988d44f7d))
* **deps:** update dependency ubuntu to v24 ([71b3ec7](https://github.com/position-pal/user-service/commit/71b3ec7aa0c2250b47f77e18a8137aa900e9894a))
* do not execute checks against generated code ([#44](https://github.com/position-pal/user-service/issues/44)) ([80de1b3](https://github.com/position-pal/user-service/commit/80de1b3f8572d45178ef567752f74e8423d25134))

### Refactoring

* **grpc:** switch to kotlin implementation of grpc ([#37](https://github.com/position-pal/user-service/issues/37)) ([03338ee](https://github.com/position-pal/user-service/commit/03338eec6f6ae4da30e1c0390bcf514f4eea5012))

## [1.1.0](https://github.com/position-pal/user-service/compare/1.0.0...1.1.0) (2024-09-22)

### Features

* **group:** added support for creating and managing groups with protobuf and grpc ([#32](https://github.com/position-pal/user-service/issues/32)) ([b96ee72](https://github.com/position-pal/user-service/commit/b96ee724539efa7b5eb0540d3dd52be4d4f49907))

### Dependency updates

* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.107 ([24613af](https://github.com/position-pal/user-service/commit/24613af45fda4c664d9aba590b75e7b918ea5428))
* **deps:** update grpc-java monorepo to v1.51.3 ([f3b19cd](https://github.com/position-pal/user-service/commit/f3b19cd0f983f73a5b5d19c20334e7ba008c3779))
* **deps:** update grpc-java monorepo to v1.68.0 ([21d6bd6](https://github.com/position-pal/user-service/commit/21d6bd69b66a2fbf9650996e75485d545e1da81e))
* **deps:** update proto to v3.21.12 ([ed8e3bd](https://github.com/position-pal/user-service/commit/ed8e3bd6e84e8df8b18443582973ff636d123f5e))
* **deps:** update proto to v3.25.5 ([efe9ad3](https://github.com/position-pal/user-service/commit/efe9ad3824053877aa1356ec467b84597d6a147e))
* **deps:** update proto to v4 ([c8b7f06](https://github.com/position-pal/user-service/commit/c8b7f06ac3e3d068a693190536eeacfe20dc0a14))

## 1.0.0 (2024-09-21)

### Features

* **user:** user service with protobuf and grpc ([#26](https://github.com/position-pal/user-service/issues/26)) ([bec3589](https://github.com/position-pal/user-service/commit/bec35892f3d4e0468a09cf1e705b329c6714bc19))

### Dependency updates

* **deps:** update dependency gradle to v8.10 ([0fbfbd0](https://github.com/position-pal/user-service/commit/0fbfbd0236c4976c916cced7d3792385a92cdee7))
* **deps:** update dependency gradle to v8.10.1 ([b724d75](https://github.com/position-pal/user-service/commit/b724d75969299621f04cb52c0e887896665dfa83))
* **deps:** update dependency org.mockito:mockito-core to v5.13.0 ([cf66fa2](https://github.com/position-pal/user-service/commit/cf66fa26f10bc008c321192e619e59c7b36d0cef))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.101 ([4ffecf9](https://github.com/position-pal/user-service/commit/4ffecf99ac4dd760440c582ea55d2555d5f13dc3))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.102 ([7f6916b](https://github.com/position-pal/user-service/commit/7f6916bf88135604fad7bb82901617ea24aedae2))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.103 ([5e34f6a](https://github.com/position-pal/user-service/commit/5e34f6aafae3df928293de35bca6314dbfa22566))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.104 ([792956d](https://github.com/position-pal/user-service/commit/792956d2f474dac091bb73638f1031d09526412b))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.105 ([9726af9](https://github.com/position-pal/user-service/commit/9726af94330babaa777609cdd3c9302d4e88aaba))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.106 ([36b2970](https://github.com/position-pal/user-service/commit/36b297063ce13cce1818127c44b3132f572c2dc0))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.96 ([1c4734a](https://github.com/position-pal/user-service/commit/1c4734aeca2fe2f5bc93e320c24e00ebe9c09699))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.98 ([a64f154](https://github.com/position-pal/user-service/commit/a64f154fe29ad6e3dc2d7be69252937eb1857241))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.99 ([5dafbd1](https://github.com/position-pal/user-service/commit/5dafbd1631cd9c1604136e47e04a43483a34872b))
* **deps:** update kotlin monorepo to v2.0.20 ([d5deede](https://github.com/position-pal/user-service/commit/d5deedefc5b787df5cf823146c7ac26f47ec4b37))
* **deps:** update node.js to 20.17 ([0552644](https://github.com/position-pal/user-service/commit/05526444901116872854d3aad7ba5e06250df5ca))
* **deps:** update plugin com.gradle.develocity to v3.18 ([ca64d9c](https://github.com/position-pal/user-service/commit/ca64d9c1049f9f5d037781222bbb81c4b5b3769d))
* **deps:** update plugin com.gradle.develocity to v3.18.1 ([dbd1aa7](https://github.com/position-pal/user-service/commit/dbd1aa7f8ee61c3e710596ae5ea9b0baec3fd996))
* **deps:** update plugin kotlin-qa to v0.65.2 ([3a8fa55](https://github.com/position-pal/user-service/commit/3a8fa5581f057935fb0c6c8175b29748b1c7cf2d))
* **deps:** update plugin kotlin-qa to v0.67.1 ([057ef68](https://github.com/position-pal/user-service/commit/057ef686a53b2377a74739617a22f96b89b75dba))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v2.0.12 ([8fb00bf](https://github.com/position-pal/user-service/commit/8fb00bfcd56d28fcfef1f8455dbb63a5135d8994))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v2.0.9 ([8f80ed9](https://github.com/position-pal/user-service/commit/8f80ed9379f47addae0cb037bf3950bfced93132))

### Build and continuous integration

* **deps:** update actions/setup-java digest to 2dfa201 ([4d58981](https://github.com/position-pal/user-service/commit/4d589814e9083345fd2c0bee61eb50d248cbd5d3))
* **deps:** update actions/setup-node action to v4.0.4 ([40aaaa7](https://github.com/position-pal/user-service/commit/40aaaa7fca8e793e01377d84bfec352755ebdc97))

### General maintenance

* update readme ([0b1ae96](https://github.com/position-pal/user-service/commit/0b1ae96e854ee98e5383590709d1bb1461715da8))

## 1.0.0 (2024-07-09)

### Dependency updates

* **deps:** update dependency gradle to v8.8 ([932f746](https://github.com/position-pal/kotlin-template/commit/932f746ca9bc232809121e1f1adbb21c83dd1dfb))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.86 ([#13](https://github.com/position-pal/kotlin-template/issues/13)) ([74fa461](https://github.com/position-pal/kotlin-template/commit/74fa461e00d589e39f284b07f560831d16b888a8))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.87 ([#14](https://github.com/position-pal/kotlin-template/issues/14)) ([06cfe7a](https://github.com/position-pal/kotlin-template/commit/06cfe7a1025228a9549065a1dfe25783dfa28da0))
* **deps:** update node.js to 20.15 ([#11](https://github.com/position-pal/kotlin-template/issues/11)) ([227a513](https://github.com/position-pal/kotlin-template/commit/227a513f1017b703aba0f90bdbab7a8abc96face))

### Documentation

* Addition of automatic generation of documentation  ([#15](https://github.com/position-pal/kotlin-template/issues/15)) ([3e4021d](https://github.com/position-pal/kotlin-template/commit/3e4021dc118b058e0ba17742b8b9c0d1df22380d))

### Build and continuous integration

* **deps:** pin dependencies ([c3b47c2](https://github.com/position-pal/kotlin-template/commit/c3b47c2267845db211b450041c1624720af8321f))
* **deps:** pin dependencies ([#10](https://github.com/position-pal/kotlin-template/issues/10)) ([8460f5e](https://github.com/position-pal/kotlin-template/commit/8460f5e67f70c741ec8fee94f514cb068e1cadfa))
* **deps:** pin dependencies ([#16](https://github.com/position-pal/kotlin-template/issues/16)) ([0facc7e](https://github.com/position-pal/kotlin-template/commit/0facc7ec98d4a636b1e9eba36ebcfdd91713cec5))
* **deps:** update actions/checkout action to v4 ([#17](https://github.com/position-pal/kotlin-template/issues/17)) ([420a43a](https://github.com/position-pal/kotlin-template/commit/420a43af202c34982c801247ed12522ac5c4c7c7))
* **deps:** update actions/setup-java action to v4 ([#18](https://github.com/position-pal/kotlin-template/issues/18)) ([aa8d773](https://github.com/position-pal/kotlin-template/commit/aa8d7730a330bba6d090b97a0a30dce9c436b01a))
* PP-16 integration with Mergify ([#6](https://github.com/position-pal/kotlin-template/issues/6)) ([85d0c02](https://github.com/position-pal/kotlin-template/commit/85d0c021db91683dfd2a82e5fd8ab4910bf9cc00))
* PP-17 sem release, update needs for workflow  ([#12](https://github.com/position-pal/kotlin-template/issues/12)) ([397851a](https://github.com/position-pal/kotlin-template/commit/397851a120c64bd452efaeeac448beac582730e8))
* treat warnings as errors and rename plugin name ([f3c949a](https://github.com/position-pal/kotlin-template/commit/f3c949a0808ce4f1c3b4db22e2f6353d0136cbde))
* update ci and mergify ([#8](https://github.com/position-pal/kotlin-template/issues/8)) ([b8565cb](https://github.com/position-pal/kotlin-template/commit/b8565cb882c6f0868ff59dc0a4373391a3db607c))

### General maintenance

* add support for submodules ([ca0759b](https://github.com/position-pal/kotlin-template/commit/ca0759b96d08996598fe1471b6cccc7e27301916))
* configure renovate ([817bfe8](https://github.com/position-pal/kotlin-template/commit/817bfe8691bf7a4215e3df204a6bdf13ac93c259))
* initial commit ([9c44e67](https://github.com/position-pal/kotlin-template/commit/9c44e6791c10d7523585c70f29d23bdde12a2fc6))
* mergify configuration file added ([c61ad89](https://github.com/position-pal/kotlin-template/commit/c61ad895e4189f551b35c92e79dfea59fe2bd622))
* **mergify:** require label rebase to automatically rebase ([3804e25](https://github.com/position-pal/kotlin-template/commit/3804e25b8b914a18ca9505155bb77a75632513f4))
* **readme:** add readme title and simple description ([fd33402](https://github.com/position-pal/kotlin-template/commit/fd334027e99c489120107102fe06c6c8cb616a40))
* remove maxHeapSize limitation ([f40f653](https://github.com/position-pal/kotlin-template/commit/f40f653651ae0c6b7629dab8cfa1883a4a05a884))
* test Mergify Implementation ([7850d87](https://github.com/position-pal/kotlin-template/commit/7850d8705e580043854aa4855bcd89416c59eb92))
