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
        detailedArchitecture: "Arquitectura de microservicios con API RESTful, base de datos NoSQL para flexibilidad y frontend React.js.",
        images: ["assets/crm-mockup-1.png", "assets/crm-mockup-2.png"], // Ejemplo de imágenes
        price_cop: 499000.00,
        status: "Available"
    },
    {
        id: 2,
        title: "ForgeLock Auth",
        product_type: "módulo",
        icon: "ph-fingerprint",
        description: "Módulo de autenticación biométrica y MFA listo para integrar en cualquier plataforma web moderna.",
        tech_stack: ["Vanilla JS", "WebAuthn"],
        detailedArchitecture: "Componente plug-and-play con API de autenticación basada en JWT y soporte para WebAuthn (biometría, FIDO2).",
        images: [], // No hay imágenes específicas para este módulo
        price_cop: 180000.00,
        status: "LTS"
    },
    {
        id: 3,
        title: "SecureVault Storage",
        product_type: "servicio",
        icon: "ph-hard-drive",
        description: "Solución de almacenamiento en la nube ultra-segura con cifrado de datos en reposo y en tránsito.",
        tech_stack: ["Go", "AWS S3", "Kubernetes"],
        detailedArchitecture: "Infraestructura distribuida en la nube, con cifrado AES-256 y gestión de claves KMS.",
        images: ["assets/vault-mockup-1.png"],
        price_cop: 750000.00,
        status: "Available"
    }
];

export default class ProductModel {
    static getAll() {
        return [...productsData];
    }
}