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
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("note.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("note.noteBookBeanTransformer")
    public BeanTransformer<NoteBook, JSFixedFastJsonNoteBook> noteBookBeanTransformer() {
        return new MapStructBeanTransformer<>(NoteBook.class, JSFixedFastJsonNoteBook.class, FastJsonMapper.class);
    }

    @Bean("note.dispNoteBookBeanTransformer")
    public BeanTransformer<DispNoteBook, JSFixedFastJsonDispNoteBook> dispNoteBookBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNoteBook.class, JSFixedFastJsonDispNoteBook.class, FastJsonMapper.class
        );
    }

    @Bean("note.ponbBeanTransformer")
    public BeanTransformer<Ponb, JSFixedFastJsonPonb> ponbBeanTransformer() {
        return new MapStructBeanTransformer<>(Ponb.class, JSFixedFastJsonPonb.class, FastJsonMapper.class);
    }

    @Bean("note.dispPonbBeanTransformer")
    public BeanTransformer<DispPonb, JSFixedFastJsonDispPonb> dispPonbBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPonb.class, JSFixedFastJsonDispPonb.class, FastJsonMapper.class);
    }

    @Bean("note.noteNodeBeanTransformer")
    public BeanTransformer<NoteNode, JSFixedFastJsonNoteNode> noteNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(NoteNode.class, JSFixedFastJsonNoteNode.class, FastJsonMapper.class);
    }

    @Bean("note.dispNoteNodeBeanTransformer")
    public BeanTransformer<DispNoteNode, JSFixedFastJsonDispNoteNode> dispNoteNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNoteNode.class, JSFixedFastJsonDispNoteNode.class, FastJsonMapper.class
        );
    }

    @Bean("note.noteItemBeanTransformer")
    public BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer() {
        return new MapStructBeanTransformer<>(NoteItem.class, JSFixedFastJsonNoteItem.class, FastJsonMapper.class);
    }

    @Bean("note.dispNoteItemBeanTransformer")
    public BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNoteItem.class, JSFixedFastJsonDispNoteItem.class, FastJsonMapper.class
        );
    }

    @Bean("note.attachmentFileInfoBeanTransformer")
    public BeanTransformer<AttachmentFileInfo, JSFixedFastJsonAttachmentFileInfo> attachmentFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AttachmentFileInfo.class, JSFixedFastJsonAttachmentFileInfo.class, FastJsonMapper.class
        );
    }
}
