export default class ProductService {
    static async fetchProducts() {
        try {
            // Simulación de futura API
            const { productsData } = await import('../models/ProductModel.js');
            return Promise.resolve([...productsData]);
        } catch (error) {
            console.error("Error en ProductService:", error);
        }
    }
}