import AppConfig from '../config/appConfig.js';

/**
 * PurchaseService - Maneja la comunicación con el API de compras.
 */
export default class PurchaseService {
    /**
     * Envía la solicitud de compra al backend.
     * @param {FormData} formData - Datos del formulario incluyendo el archivo.
     * @returns {Promise<Object>}
     * El formData debe contener 'paymentData' (JSON string) y 'receipt' (File)
     */
    static async submitPurchase(formData) {
        const response = await fetch(`${AppConfig.API_BASE_URL}/api/payments`, {
            method: 'POST',
            body: formData // FormData se envía directamente, fetch se encarga del Content-Type
        });
        if (!response.ok) {
            throw new Error(`Error en el servidor: ${response.status} ${response.statusText}`);
        }
        return await response.json();
    }
}
