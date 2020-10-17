package com.kaldie.eveindustry.Service.Killboard;

import java.util.Date;
import java.util.List;

import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMaterialsEntity;
import com.kaldie.eveindustry.Repository.RequiredMaterials.RequiredMaterialsRepository;
import com.kaldie.eveindustry.Repository.TypeID.TypeId;
import com.kaldie.eveindustry.Repository.Zkillboard.DestroyedContent;
import com.kaldie.eveindustry.Repository.Zkillboard.DestroyedItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KillboardServiceImp {

    @Autowired
    RequiredMaterialsRepository requiredMaterialsRepository;

    @Autowired
    DestroyedItemsRepository destroyedItemsRepository;

	public void addMaterials(TypeId typeId, long quentity, Date date) {
        List<RequiredMaterialsEntity> materials = requiredMaterialsRepository.getRequiredMaterials(typeId.getId());
        materials.stream().forEach(material -> {
            DestroyedContent content = new DestroyedContent(
                material.getRequiredMaterial(), 
                date, material.getQuantity() * quentity);

            destroyedItemsRepository.save(content);
        });
	}

}
