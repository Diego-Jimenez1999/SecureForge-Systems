/**
 * Modelo de datos para los productos de SecureForge.
 */
export const productsData = [
    {
        id: 1,
        title: "SecureGuard CRM",
        product_type: "saas",
        icon: "ph-shield-plus",
        description: "Sistema de gestión de clientes con encriptación de extremo a extremo y auditoría de accesos integrada.",
        tech_stack: ["ES6", "Node.js", "MVC"],
        price_cop: 499000.00,
        status: "Available"
    },
    {
        id: 2,
        title: "ForgeLock Auth",
        product_type: "library",
        icon: "ph-fingerprint",
        description: "Módulo de autenticación biométrica y MFA listo para integrar en cualquier plataforma web moderna.",
        tech_stack: ["Vanilla JS", "WebAuthn"],
        price_cop: 180000.00,
        status: "LTS"
    }
];

export default class ProductModel {
    static getAll() {
        return [...productsData];
    }
}