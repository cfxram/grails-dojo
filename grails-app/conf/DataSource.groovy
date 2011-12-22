dataSource {
  pooled = true
  username = "sa"
  password = ""

  // Use for grails 2.0
  driverClassName = "org.h2.Driver"

  // Use for grails 1.3.7
  //driverClassName = "org.hsqldb.jdbcDriver"
}

hibernate {
  cache.use_second_level_cache = true
  cache.use_query_cache = true
  cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

environments {

  development {
    dataSource {
      dbCreate = "create-drop"

      //Use for grails 1.3.7
      //url = "jdbc:hsqldb:mem:devDB"

      // Use for grails 2.0
      url = "jdbc:h2:mem:devDb;MVCC=TRUE"
    }
  }
}
