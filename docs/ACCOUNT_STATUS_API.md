# Account Status API (Retell Voice AI)

Endpoint for the Retell voice AI agent to check whether a caller’s account is active (e.g. “Is my account active?”).

## Endpoint

- **Method:** `POST`
- **Path:** `/api/account/status`
- **Consumes:** `application/json`
- **Produces:** `application/json`

## Authentication

Server-to-server only. Send the API key in the header:

- **Header:** `X-API-Key: <your-api-key>`

Configure the key in `application.properties` or via environment variable:

- Property: `retell.api.key`
- Env: `RETELL_API_KEY`

If the key is missing or invalid, the API returns **401 Unauthorized**.

## Request body

| Field             | Type   | Required | Description                                      |
|------------------|--------|----------|--------------------------------------------------|
| `user_identifier`| string | Yes      | Email, phone number, or numeric account ID      |

**Supported identifiers:**

- **Email** – case-insensitive (e.g. `user@example.com`)
- **Phone number** – digits (and optional `+`) only; spaces/dashes are stripped
- **Account ID** – numeric string (e.g. `"12345"`)

Example:

```json
{ "user_identifier": "user@example.com" }
```

## Responses

### 200 OK – Account found

- **Active:** `{ "active": true, "status": "active", "message": "Account in good standing." }`
- **Inactive (e.g. suspended):** `{ "active": false, "status": "suspended", "message": "Account is suspended. Please contact support." }`

The caller can rely on `active` (boolean) or `status` (string).

### 400 Bad Request

Missing or invalid `user_identifier` (e.g. empty). Body includes a clear error message.

### 401 Unauthorized

Missing or invalid `X-API-Key`. Caller should tell the user to contact support.

### 404 Not Found

No account found for the given `user_identifier`. Body example:

```json
{ "status": "inactive", "message": "No account found for the given identifier.", "error": "NOT_FOUND" }
```

Caller should ask the user to verify the identifier.

## Definition of “active”

An account is **active** if it exists and its status is `ACTIVE` (not `SUSPENDED` or `DELETED`).

## Performance

The endpoint is intended to respond in under 5 seconds to avoid voice agent timeouts (caller uses a 10s timeout).
