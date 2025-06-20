package com.example.day5_search;




import com.google.gson.annotations.SerializedName;
import androidx.room.Entity; // 导入 Room Entity 注解
import androidx.room.PrimaryKey; // 导入 Room PrimaryKey 注解



// @Entity 注解表示这是一个Room数据库实体，tableName指定表名
@Entity(tableName = "game_info")
public class GameInfo {



    // @PrimaryKey 注解表示这是主键。
    // autoGenerate = true 表示主键值会自动生成（自增长）。
    // 如果您想使用API返回的gameId作为主键，且gameId保证唯一且非空，可以这样：
    // @PrimaryKey
    // @NonNull // 主键必须是非空的
    // @SerializedName("id")
    // private String gameId;
    // 但为了简单和通用性，我们使用自增长的dbId作为Room内部主键。
    @PrimaryKey(autoGenerate = true)
    private int dbId; // Room 内部使用的自增长主键


    @SerializedName("id")
    private String gameId; // 游戏ID

    // 将 @SerializedName("name") 修改为 @SerializedName("gameName")
    @SerializedName("gameName")
    private String gameName; // 游戏名称

    // 修正这里：将 @SerializedName("iconUrl") 修改为 @SerializedName("icon")
    @SerializedName("icon")
    private String gameIconUrl;

    // 将 @SerializedName("description") 修改为 @SerializedName("introduction")
    @SerializedName("introduction")
    private String gameDescription; // 游戏描述 (对应JSON中的"introduction")

    // 无参构造函数是Gson反序列化所必需的
    public GameInfo() {
    }

    // 带参构造函数，方便创建对象
    public GameInfo(String gameId, String gameName, String gameIconUrl, String gameDescription) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameIconUrl = gameIconUrl;
        this.gameDescription = gameDescription;
    }


    // Getters and Setters for all fields (Room和Gson都需要)
    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }



    // Getters and Setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameIconUrl() {
        return gameIconUrl;
    }

    public void setGameIconUrl(String gameIconUrl) {
        this.gameIconUrl = gameIconUrl;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "gameId='" + gameId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameIconUrl='" + gameIconUrl + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                '}';
    }
}
