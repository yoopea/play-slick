package play.api.db.slick

import javax.sql.{ DataSource => SqlDataSource }

class DBSourceProvider(val datasource: SqlDataSource, val name: String)
