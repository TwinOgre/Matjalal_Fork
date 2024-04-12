/** @type {import('next').NextConfig} */
const nextConfig = {
    async redirects() {
        return [
          {
            source: "/",
            destination: "/home/index",
            permanent: true,
          },
        ];
      },
};

export default nextConfig;
