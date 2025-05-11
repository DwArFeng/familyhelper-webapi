package com.dwarfeng.familyhelper.webapi.node.configuration.note;

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
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Mapper
public interface FastJsonMapper {

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
