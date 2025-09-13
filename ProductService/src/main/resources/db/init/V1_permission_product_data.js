// V5_permission_product_data.js
db = db.getSiblingDB("ecomhub");
// Kiểm tra và tạo collection nếu chưa tồn tại
db.createCollection("permissions", {
    validator: {
        $jsonSchema: {
            bsonType: "object",
            required: ["id", "name", "key", "description"],
            properties: {
                id: { bsonType: "string" },
                name: { bsonType: "string" },
                key: { bsonType: "string" },
                description: { bsonType: "string" }
            }
        }
    }
});

// Dữ liệu permission cho product service
db.permissions.insertMany([
    {
        id: UUID(),
        name: "View product",
        key: "role.read",
        description: "Allows viewing the list and details of products."
    },
    {
        id: UUID(),
        name: "Create product",
        key: "role.create",
        description: "Allows creating a product."
    },
    {
        id: UUID(),
        name: "Update product",
        key: "role.update",
        description: "Allows updating a product."
    },
    {
        id: UUID(),
        name: "Delete product",
        key: "role.delete",
        description: "Allows deleting a product."
    },
    {
        id: UUID(),
        name: "Purchase product",
        key: "role.purchase",
        description: "Allows purchasing a product variant."
    }
]);

// Đảm bảo index duy nhất cho trường 'key'
db.permissions.createIndex({ key: 1 }, { unique: true });