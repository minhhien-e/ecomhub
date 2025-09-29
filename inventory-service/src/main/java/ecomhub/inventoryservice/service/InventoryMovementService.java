package ecomhub.inventoryservice.service;

import ecomhub.inventoryservice.dto.CreateMovementRequest;
import ecomhub.inventoryservice.model.InventoryMovement;
import ecomhub.inventoryservice.repository.InventoryMovementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class InventoryMovementService {

    private final InventoryMovementRepository repository;

    public InventoryMovementService(InventoryMovementRepository repository){
        this.repository = repository;
    }

//    Ghi biến động vào MOngoDB
    public InventoryMovement createMovement(CreateMovementRequest request){
        InventoryMovement movement = new InventoryMovement(
                request.getVariantId(),
                request.getShopId(),
                request.getQuantityChange(),
                request.getType()
        );
        InventoryMovement save = repository.save(movement);
        return save;
    }

//    Lấy cái history theo varient
    public List<InventoryMovement> getMovement(UUID variantId, UUID shopId){
        return repository.findByVariantIdAndShopIdOrderByCreatedAtDesc(variantId, shopId);
    }

//    Lấy cái history trong khoảng thời gian nào đó
    public List<InventoryMovement> gwtMovementsInRange(UUID variantId, UUID shopId, LocalDateTime from, LocalDateTime to){
        return repository.findByVariantIdAndShopIdAndCreatedAtBetween(variantId, shopId, from, to);
    }
}
