package com.dwarfeng.familyhelper.webapi.node.configuration.note;

import com.dwarfeng.familyhelper.note.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.note.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.BeanMapper;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispPonb;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispPonb;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("note.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("note.noteBookBeanTransformer")
    public BeanTransformer<NoteBook, JSFixedFastJsonNoteBook> noteBookBeanTransformer() {
        return new MapStructBeanTransformer<>(NoteBook.class, JSFixedFastJsonNoteBook.class, BeanMapper.class);
    }

    @Bean("note.dispNoteBookBeanTransformer")
    public BeanTransformer<DispNoteBook, JSFixedFastJsonDispNoteBook> dispNoteBookBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNoteBook.class, JSFixedFastJsonDispNoteBook.class, BeanMapper.class
        );
    }

    @Bean("note.ponbBeanTransformer")
    public BeanTransformer<Ponb, JSFixedFastJsonPonb> ponbBeanTransformer() {
        return new MapStructBeanTransformer<>(Ponb.class, JSFixedFastJsonPonb.class, BeanMapper.class);
    }

    @Bean("note.dispPonbBeanTransformer")
    public BeanTransformer<DispPonb, JSFixedFastJsonDispPonb> dispPonbBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPonb.class, JSFixedFastJsonDispPonb.class, BeanMapper.class);
    }

    @Bean("note.noteNodeBeanTransformer")
    public BeanTransformer<NoteNode, JSFixedFastJsonNoteNode> noteNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(NoteNode.class, JSFixedFastJsonNoteNode.class, BeanMapper.class);
    }

    @Bean("note.dispNoteNodeBeanTransformer")
    public BeanTransformer<DispNoteNode, JSFixedFastJsonDispNoteNode> dispNoteNodeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNoteNode.class, JSFixedFastJsonDispNoteNode.class, BeanMapper.class
        );
    }

    @Bean("note.noteItemBeanTransformer")
    public BeanTransformer<NoteItem, JSFixedFastJsonNoteItem> noteItemBeanTransformer() {
        return new MapStructBeanTransformer<>(NoteItem.class, JSFixedFastJsonNoteItem.class, BeanMapper.class);
    }

    @Bean("note.dispNoteItemBeanTransformer")
    public BeanTransformer<DispNoteItem, JSFixedFastJsonDispNoteItem> dispNoteItemBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNoteItem.class, JSFixedFastJsonDispNoteItem.class, BeanMapper.class
        );
    }

    @Bean("note.attachmentFileInfoBeanTransformer")
    public BeanTransformer<AttachmentFileInfo, JSFixedFastJsonAttachmentFileInfo> attachmentFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AttachmentFileInfo.class, JSFixedFastJsonAttachmentFileInfo.class, BeanMapper.class
        );
    }
}
