package com.dwarfeng.familyhelper.webapi.node.configuration.note;

import com.dwarfeng.familyhelper.note.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.note.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispNoteBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispNoteItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispNoteNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.note.JSFixedFastJsonDispPonb;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.note.DispPonb;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("note.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("note.noteBookBeanTransformer")
    public BeanTransformer<NoteBook, JSFixedFastJsonNoteBook> noteBookBeanTransformer() {
        return new DozerBeanTransformer<>(NoteBook.class, JSFixedFastJsonNoteBook.class, mapper);
    }

    @Bean("note.dispNoteBookBeanTransformer")
    public BeanTransformer<DispNoteBook, JSFixedFastJsonDispNoteBook> dispNoteBookBeanTransformer() {
        return new DozerBeanTransformer<>(DispNoteBook.class, JSFixedFastJsonDispNoteBook.class, mapper);
    }

    @Bean("note.ponbBeanTransformer")
    public BeanTransformer<Ponb, JSFixedFastJsonPonb> ponbBeanTransformer() {
        return new DozerBeanTransformer<>(Ponb.class, JSFixedFastJsonPonb.class, mapper);
    }

    @Bean("note.dispPonbBeanTransformer")
    public BeanTransformer<DispPonb, JSFixedFastJsonDispPonb> dispPonbBeanTransformer() {
        return new DozerBeanTransformer<>(DispPonb.class, JSFixedFastJsonDispPonb.class, mapper);
    }

    @Bean("note.noteNodeBeanTransformer")
    public BeanTransformer<NoteNode, JSFixedFastJsonNoteNode> noteNodeBeanTransformer() {
        return new DozerBeanTransformer<>(NoteNode.class, JSFixedFastJsonNoteNode.class, mapper);
    }

    @Bean("note.dispNoteNodeBeanTransformer")
    public BeanTransformer<DispNoteNode, JSFixedFastJsonDispNoteNode> dispNoteNodeBeanTransformer() {
        return new DozerBeanTransformer<>(DispNoteNode.class, JSFixedFastJsonDispNoteNode.class, mapper);
    }

    @Bean("note.noteItemBeanTransformer")
    public BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer() {
        return new DozerBeanTransformer<>(NoteItem.class, JSFixedFastJsonNoteItem.class, mapper);
    }

    @Bean("note.dispNoteItemBeanTransformer")
    public BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer() {
        return new DozerBeanTransformer<>(DispNoteItem.class, JSFixedFastJsonDispNoteItem.class, mapper);
    }

    @Bean("note.attachmentFileInfoBeanTransformer")
    public BeanTransformer<AttachmentFileInfo, JSFixedFastJsonAttachmentFileInfo> attachmentFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(AttachmentFileInfo.class, JSFixedFastJsonAttachmentFileInfo.class, mapper);
    }
}
