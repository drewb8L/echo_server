package com.babcock.http

import java.nio.file.Path
import java.nio.file.Paths

data class FilePath(val path:String){
    val fullPath:Path = Paths.get(path)
}
