class config {
    // 获取用户缓存位置
    def CachePath = System.getProperty("user.home") + File.separator + ".cache"
    // 获取当前操作系统
    def OS = System.getProperty("os.name")
    // 获取当前系统架构
    def Arch = System.getProperty("os.arch")
    // 获取当前系统版本
    def Version = System.getProperty("os.version")
    // 获取当前系统名称
    def Name = System.getProperty("os.name")
}