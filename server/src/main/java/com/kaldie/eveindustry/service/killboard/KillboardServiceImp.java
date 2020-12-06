package com.kaldie.eveindustry.service.killboard;

import java.util.Date;
import java.util.List;

import com.kaldie.eveindustry.repository.required_materials.RequiredMaterialsEntity;
import com.kaldie.eveindustry.repository.required_materials.RequiredMaterialsRepository;
import com.kaldie.eveindustry.repository.type_id.TypeId;
import com.kaldie.eveindustry.repository.zkillboard.DestroyedContent;
import com.kaldie.eveindustry.repository.zkillboard.DestroyedItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KillboardServiceImp {

    @Autowired
    RequiredMaterialsRepository requiredMaterialsRepository;

    @Autowired
    DestroyedItemsRepository destroyedItemsRepository;

	public void addDestroyedMaterials(TypeId typeId, long quentity, Date date) {
        List<RequiredMaterialsEntity> materials = requiredMaterialsRepository.getRequiredMaterials(typeId.getId());
        materials.stream().filter(material -> material.getRelativeDepth() == 1).forEach(material -> {
            DestroyedContent content = new DestroyedContent(
                material.getRequiredMaterial(), 
                date, material.getQuantity() * quentity);

            destroyedItemsRepository.save(content);
        });
	}

}
