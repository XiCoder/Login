# Login
Kotlin MVVM Compose Login
项目主要包含登录和密码等级评级

- Kotlin
- UI使用Compose绘制
- 账号输入错误页面即时提示，按钮置灰
- 添加单元测试 PasswordUtilsTest、PatternUtilsTest

密码等级规则：
密码分为简单、中等、强、非常强四个等级

连续的判定：相邻3个字符为键盘顺序、字母顺序、数字顺序或3个相同

密码等级举例：

简单
- 密码长度小于6或者字符全部相等
- 纯数字、纯字母

中等
- 数字与字母组合连续
- 数字与字母组合不连续
- 数字与字母组合包含大小写
- 数字与字母组合长度大于12
- 数字与字母组合包含大小写连续

强
- 数字与字母组合包含大小写不连续,长度大于6
- 数字与字母组合包含大小写不连续，长度大于12
- 数字与字母组合包含大小写不连续，长度大于6,特殊字符
- 数字与字母组合包含大小写连续，长度大于6,特殊字符
- 数字与字母组合包含大小写连续，长度大于12,特殊字符

非常强
- 数字与字母组合包含大小写不连续，长度大于12,包含特殊字符
