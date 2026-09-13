package com.dwarfeng.familyhelper.webapi.node.configuration.fileio;

import com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp.JSFixedFastJsonDispExportTask;
import com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp.JSFixedFastJsonDispImportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispExportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispImportTask;
import com.dwarfeng.fileio.sdk.bean.entity.*;
import com.dwarfeng.fileio.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("fileio.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("fileio.exportConfBeanTransformer")
    public BeanTransformer<ExportConf, JSFixedFastJsonExportConf> exportConfBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExportConf.class, JSFixedFastJsonExportConf.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exportFileInfoBeanTransformer")
    public BeanTransformer<ExportFileInfo, JSFixedFastJsonExportFileInfo> exportFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExportFileInfo.class, JSFixedFastJsonExportFileInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exportMetadataBeanTransformer")
    public BeanTransformer<ExportMetadata, JSFixedFastJsonExportMetadata> exportMetadataBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExportMetadata.class, JSFixedFastJsonExportMetadata.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exportTaskBeanTransformer")
    public BeanTransformer<ExportTask, JSFixedFastJsonExportTask> exportTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExportTask.class, JSFixedFastJsonExportTask.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exportTaskSettingBeanTransformer")
    public BeanTransformer<ExportTaskSetting, JSFixedFastJsonExportTaskSetting> exportTaskSettingBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExportTaskSetting.class, JSFixedFastJsonExportTaskSetting.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exportTemplateInfoBeanTransformer")
    public BeanTransformer<ExportTemplateInfo, JSFixedFastJsonExportTemplateInfo> exportTemplateInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExportTemplateInfo.class, JSFixedFastJsonExportTemplateInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exporterInfoBeanTransformer")
    public BeanTransformer<ExporterInfo, JSFixedFastJsonExporterInfo> exporterInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExporterInfo.class, JSFixedFastJsonExporterInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.exporterSupportBeanTransformer")
    public BeanTransformer<ExporterSupport, FastJsonExporterSupport> exporterSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ExporterSupport.class, FastJsonExporterSupport.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.writerSupportBeanTransformer")
    public BeanTransformer<WriterSupport, FastJsonWriterSupport> writerSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                WriterSupport.class, FastJsonWriterSupport.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.writerInfoBeanTransformer")
    public BeanTransformer<WriterInfo, JSFixedFastJsonWriterInfo> writerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                WriterInfo.class, JSFixedFastJsonWriterInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importConfBeanTransformer")
    public BeanTransformer<ImportConf, JSFixedFastJsonImportConf> importConfBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImportConf.class, JSFixedFastJsonImportConf.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importFileInfoBeanTransformer")
    public BeanTransformer<ImportFileInfo, JSFixedFastJsonImportFileInfo> importFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImportFileInfo.class, JSFixedFastJsonImportFileInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importMetadataBeanTransformer")
    public BeanTransformer<ImportMetadata, JSFixedFastJsonImportMetadata> importMetadataBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImportMetadata.class, JSFixedFastJsonImportMetadata.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importTaskBeanTransformer")
    public BeanTransformer<ImportTask, JSFixedFastJsonImportTask> importTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImportTask.class, JSFixedFastJsonImportTask.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importTaskSettingBeanTransformer")
    public BeanTransformer<ImportTaskSetting, JSFixedFastJsonImportTaskSetting> importTaskSettingBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImportTaskSetting.class, JSFixedFastJsonImportTaskSetting.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importTemplateInfoBeanTransformer")
    public BeanTransformer<ImportTemplateInfo, JSFixedFastJsonImportTemplateInfo> importTemplateInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImportTemplateInfo.class, JSFixedFastJsonImportTemplateInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importerInfoBeanTransformer")
    public BeanTransformer<ImporterInfo, JSFixedFastJsonImporterInfo> importerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImporterInfo.class, JSFixedFastJsonImporterInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.importerSupportBeanTransformer")
    public BeanTransformer<ImporterSupport, FastJsonImporterSupport> importerSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ImporterSupport.class, FastJsonImporterSupport.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.readerSupportBeanTransformer")
    public BeanTransformer<ReaderSupport, FastJsonReaderSupport> readerSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ReaderSupport.class, FastJsonReaderSupport.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.readerInfoBeanTransformer")
    public BeanTransformer<ReaderInfo, JSFixedFastJsonReaderInfo> readerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ReaderInfo.class, JSFixedFastJsonReaderInfo.class,
                com.dwarfeng.fileio.sdk.bean.BeanMapper.class
        );
    }

    @Bean("fileio.dispExportTaskBeanTransformer")
    public BeanTransformer<DispExportTask, JSFixedFastJsonDispExportTask> dispExportTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispExportTask.class, JSFixedFastJsonDispExportTask.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.BeanMapper.class
        );
    }

    @Bean("fileio.dispImportTaskBeanTransformer")
    public BeanTransformer<DispImportTask, JSFixedFastJsonDispImportTask> dispImportTaskBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispImportTask.class, JSFixedFastJsonDispImportTask.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.BeanMapper.class
        );
    }
}
