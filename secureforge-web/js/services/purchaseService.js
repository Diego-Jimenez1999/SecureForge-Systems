/**
 * PurchaseService - Maneja la comunicación con el API de compras.
 */
export default class PurchaseService {
    /**
     * Envía la solicitud de compra al backend.
     * @param {FormData} formData - Datos del formulario incluyendo el archivo.
     * @returns {Promise<Object>}
     */
    static async submitPurchase(formData) {
        // Endpoint: POST /api/compra
        // En un entorno real:
        // const response = await fetch('/api/compra', { method: 'POST', body: formData });
        // if (!response.ok) throw new Error('Error en el servidor');
        // return await response.json();

        // Simulación de Backend (Mock)
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve({ success: true, message: 'Pronto nos comunicaremos contigo' });
            }, 2000);
        });
    }
}