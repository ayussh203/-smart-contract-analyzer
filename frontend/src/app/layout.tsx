import "./globals.css";
import { AuthProvider } from "./auth/AuthContext";

export const metadata = {
  title: "My App",
  description: "Demo",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body>
        <AuthProvider>
          {children}
        </AuthProvider>
      </body>
    </html>
  );
}