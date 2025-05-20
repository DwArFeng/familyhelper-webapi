package com.dwarfeng.familyhelper.webapi.sdk.bean.note;

import com.dwarfeng.familyhelper.note.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.note.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteItem;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispNoteNode;
import com.dwarfeng.familyhelper.webapi.sdk.bean.note.disp.JSFixedFastJsonDispPonb;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispNoteNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.note.disp.DispPonb;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Mapper
public interface BeanMapper {

    JSFixedFastJsonNoteBook noteBookToJsFixedFastJson(NoteBook noteBook);

    @InheritInverseConfiguration
    NoteBook noteBookFromJsFixedFastJson(JSFixedFastJsonNoteBook jsFixedFastJsonNoteBook);

    JSFixedFastJsonDispNoteBook dispNoteBookToJsFixedFastJson(DispNoteBook dispNoteBook);

    @InheritInverseConfiguration
    DispNoteBook dispNoteBookFromJsFixedFastJson(JSFixedFastJsonDispNoteBook jsFixedFastJsonDispNoteBook);

    JSFixedFastJsonPonb ponbToJsFixedFastJson(Ponb ponb);

    @InheritInverseConfiguration
    Ponb ponbFromJsFixedFastJson(JSFixedFastJsonPonb jsFixedFastJsonPonb);

    JSFixedFastJsonDispPonb dispPonbToJsFixedFastJson(DispPonb dispPonb);

    @InheritInverseConfiguration
    DispPonb dispPonbFromJsFixedFastJson(JSFixedFastJsonDispPonb jsFixedFastJsonDispPonb);

    JSFixedFastJsonNoteNode noteNodeToJsFixedFastJson(NoteNode noteNode);

    @InheritInverseConfiguration
    NoteNode noteNodeFromJsFixedFastJson(JSFixedFastJsonNoteNode jsFixedFastJsonNoteNode);

    JSFixedFastJsonDispNoteNode dispNoteNodeToJsFixedFastJson(DispNoteNode dispNoteNode);

    @InheritInverseConfiguration
    DispNoteNode dispNoteNodeFromJsFixedFastJson(JSFixedFastJsonDispNoteNode jsFixedFastJsonDispNoteNode);

    JSFixedFastJsonNoteItem noteItemToJsFixedFastJson(NoteItem noteItem);

    @InheritInverseConfiguration
    NoteItem noteItemFromJsFixedFastJson(JSFixedFastJsonNoteItem jsFixedFastJsonNoteItem);

    JSFixedFastJsonDispNoteItem dispNoteItemToJsFixedFastJson(DispNoteItem dispNoteItem);

    @InheritInverseConfiguration
    DispNoteItem dispNoteItemFromJsFixedFastJson(JSFixedFastJsonDispNoteItem jsFixedFastJsonDispNoteItem);

    JSFixedFastJsonAttachmentFileInfo attachmentFileInfoToJsFixedFastJson(AttachmentFileInfo attachmentFileInfo);

    @InheritInverseConfiguration
    AttachmentFileInfo attachmentFileInfoFromJsFixedFastJson(
            JSFixedFastJsonAttachmentFileInfo jsFixedFastJsonAttachmentFileInfo
    );
}
