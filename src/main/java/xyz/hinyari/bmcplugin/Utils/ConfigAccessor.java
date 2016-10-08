package xyz.hinyari.bmcplugin.Utils;

import xyz.hinyari.bmcplugin.BMCPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by Hinyari_Gohan on 2016/09/25.
 */
public class ConfigAccessor
{
    private final String fileName;
    private final JavaPlugin plugin;
    private final BMCPlugin bmcPlugin;

    private File configFile;
    private FileConfiguration fileConfiguration;

    /**
     * Configファイル実装を楽にできるかもしれないクラス<br/>
     * 1.9以降のAPIにも対応してます。
     *
     * @param plugin         プラグインクラス
     * @param fileName     　Configファイルネーム
     */
    public ConfigAccessor(JavaPlugin plugin, String fileName, BMCPlugin bmcPlugin)
    {
        if(plugin == null)
        {
            throw new IllegalArgumentException("plugin cannot be null");
        }
        //非推奨だが放置
        if(!plugin.isInitialized())
        {
            throw new IllegalArgumentException("plugin must be initialized");
        }

        this.plugin = plugin;
        this.fileName = fileName;
        this.bmcPlugin = bmcPlugin;
        File dataFolder = plugin.getDataFolder();

        if(dataFolder == null)
        {
            throw new IllegalStateException();
        }

        this.configFile = new File(plugin.getDataFolder(), fileName);
    }

    public void reloadConfig()
    {
        fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig()
    {
        if(fileConfiguration == null)
        {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    public void saveConfig()
    {
        if(fileConfiguration == null || configFile == null)
        {
            return;
        }
        else
        {
            try
            {
                getConfig().save(configFile);
            }
            catch(IOException ex)
            {
                BMCPlugin.log.severe("Could not save config to " + configFile);
                ex.printStackTrace();
            }
        }
    }

    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            copyRawFileFromJar(bmcPlugin.getPluginJarFile(), configFile, fileName);
        }
    }

    /**
     * jarファイルの中に格納されているテキストファイルを、jarファイルの外にコピーするメソッド<br/>
     * ファイルをそのままコピーします。
     *
     * @param jarFile        jarファイル
     * @param targetFile     コピー先
     * @param sourceFilePath コピー元
     */
    public static void copyRawFileFromJar(File jarFile, File targetFile, String sourceFilePath)
    {
        JarFile jar = null;
        InputStream is = null;

        File parent = targetFile.getParentFile();
        if (!parent.exists())
        {
            parent.mkdirs();
        }

        try
        {
            jar = new JarFile(jarFile);
            ZipEntry zipEntry = jar.getEntry(sourceFilePath);
            is = jar.getInputStream(zipEntry);

            Files.copy(is, targetFile.toPath());
        }
        catch (IOException e)
        {
            BMCPlugin.log.severe(targetFile + "のコピーに失敗しました。");
            e.printStackTrace();
        }
        finally
        {
            if (jar != null)
            {
                try
                {
                    jar.close();
                }
                catch (IOException e)
                {
                    // do nothing.
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    // do nothing.
                }
            }
        }
    }
}
