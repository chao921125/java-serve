package ${package}.${moduleName}.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
* <p>
* ${tableComment} 实体类
* </p>
*/
@TableName("${tableName}")
public class ${entityName} implements Serializable {

private static final long serialVersionUID = 1L;

@TableId("${tableId}")
private ${tableIdType} ${tableId};

// 其他属性

// getters and setters
}