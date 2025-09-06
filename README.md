Deep Linking with QR Code Scanner (Room DB Integration)
This Android application provides a smooth way to retrieve and display product details by using deep links, Room Database for local storage, and the system QR code scanner for access.

Key Features
Local Storage with Room DB
All product data is saved in the Room Database for offline access and persistence.

Deep Linking Support
Each product record has a unique URI-style deep link that is tied to its product ID. Example:
myapp://product/{productId}

QR Code Integration

QR codes are generated to contain the deep link of each product.

Scanning a QR code launches the deep link and opens the app directly into the product details screen.

System-level QR Scanner

Uses the device’s default/system QR code scanner intent, no need for in-app camera handling.

Instant Navigation
The scanned link is resolved, the corresponding product ID is matched in the Room Database, and product details are displayed automatically.

Workflow
Product data is saved in Room Database (ProductEntity with fields like id, name, description, price, etc).

For each product, generate a deep link URL (myapp://product/123).

Convert the deep link into a QR code.

Share or print the QR code.

When a user scans the QR code with the system QR code scanner:

The system recognizes the deep link.

Android launches the app with the product deep link.

The app intercepts the deep link (via NavDeepLinkBuilder or Navigation Component deep link intent filters).

The product ID from the URI is extracted.

The app queries Room Database for matching product details.

Product details screen is displayed to the user.

Example Deep Link
text
myapp://product/101
Opens the app and loads product with ID = 101 from Room DB.

Benefits
Seamless User Experience: Users don’t need to type or search manually.

Offline Capability: Deep linking is resolved locally with Room DB (no network required).

Scalable: Works with any number of products supported in the database.

System-friendly: Relies on system QR scanner to avoid extra permissions and battery drain.

Optional Workflow Diagram (Mermaid)
You can add this snippet in README for clarity:

text
flowchart TD
    A[Product stored in Room DB] --> B[Generate Deep Link]
    B --> C[Create QR Code of Deep Link]
    C --> D[User Scans QR Code with System Scanner]
    D --> E[System Opens App via Deep Link]
    E --> F[Deep Link Handler Extracts Product ID]
    F --> G[Room DB Query for Product]
    G --> H[Show Product Details Screen]
